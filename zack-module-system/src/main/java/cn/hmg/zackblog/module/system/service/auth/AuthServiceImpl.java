package cn.hmg.zackblog.module.system.service.auth;

import cn.hmg.zackblog.framework.common.enums.CommonStatusEnum;
import cn.hmg.zackblog.framework.common.enums.UserTypeEnum;
import cn.hmg.zackblog.framework.common.exception.BusinessException;
import cn.hmg.zackblog.framework.common.utils.date.DateUtils;
import cn.hmg.zackblog.framework.common.utils.servlet.ServletUtils;
import cn.hmg.zackblog.framework.captcha.autoconfigure.CaptchaProperties;
import cn.hmg.zackblog.framework.security.autoconfigure.SecurityProperties;
import cn.hmg.zackblog.framework.security.core.pojo.LoginUser;
import cn.hmg.zackblog.framework.redis.core.utils.RedisUtils;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.AdminAuthLoginReqVO;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.AdminAuthLoginRespVO;
import cn.hmg.zackblog.module.system.convert.auth.AdminAuthConvert;
import cn.hmg.zackblog.module.system.entity.user.User;
import cn.hmg.zackblog.module.system.enums.LoginResultEnum;
import cn.hmg.zackblog.module.system.enums.LoginTypeEnum;
import cn.hmg.zackblog.module.system.service.logger.ILoginLogService;
import cn.hmg.zackblog.module.system.service.logger.dto.LoginLogCreateDTO;
import cn.hmg.zackblog.module.system.service.user.IUserService;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONUtil;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static cn.hmg.zackblog.framework.security.core.constants.RedisKeyConstant.ACCESS_TOKEN;
import static cn.hmg.zackblog.framework.security.core.constants.RedisKeyConstant.REFRESH_TOKEN;
import static cn.hmg.zackblog.module.system.enums.ErrorCodeEnum.*;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-12 17:02
 * @description: 认证服务实现类
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private CaptchaService captchaService;

    @Resource
    private IUserService userService;

    @Resource
    private ILoginLogService loginLogService;

    @Resource
    private CaptchaProperties captchaProperties;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private SecurityProperties securityProperties;

    @Override
    public AdminAuthLoginRespVO login(AdminAuthLoginReqVO adminAuthLoginReqVO, UserTypeEnum userTypeEnum) {
        //校验验证码
        verifyCaptcha(adminAuthLoginReqVO);

        //使用账号密码进行登录，进行一个用户认证
        User user = authentication(adminAuthLoginReqVO, userTypeEnum);

        //构建LoginRespVO并记录日志
        return buildLoginResponseVO(user.getId(), user.getStatus(), user.getUsername(), userTypeEnum);
    }

    @Override
    public AdminAuthLoginRespVO refreshToken(String refreshToken) {
        String refreshTokenRedisKey = REFRESH_TOKEN.format(refreshToken);
        //校验刷新令牌的有效性
        LoginUser loginUser = verifyRefreshTokenIsValid(refreshTokenRedisKey);

        //校验访问令牌是否过期
        if (DateUtils.isExpire(loginUser.getAccessTokenExpireTime())) {
            //删除访问令牌缓存
            deleteAccessTokenCache(loginUser.getAccessToken());

            //构建loginUser
            loginUser = buildLoginUser(loginUser.getUserId(), loginUser.getUsername(), loginUser.getUserType(),
                    loginUser.getStatus());


            //删除旧的刷新令牌
            redisUtils.delete(refreshTokenRedisKey);
            //把新构建的登录用户信息加入缓存
            setTokenWithUserInfoCache(loginUser);
        }
        return AdminAuthConvert.INSTANCE.convert(loginUser);
    }

    /**
     * 删除访问令牌缓存
     * @param accessToken 访问令牌
     */
    private void deleteAccessTokenCache(String accessToken){
        String accessTokenRedisKey = ACCESS_TOKEN.format(accessToken);
        if (redisUtils.exists(accessTokenRedisKey)) {
            redisUtils.delete(accessTokenRedisKey);
        }
    }

    /**
     * 校验刷新令牌的有效性
     *
     * @param refreshTokenRedisKey 刷新令牌 redis key
     * @return 登录用户信息
     */
    private LoginUser verifyRefreshTokenIsValid(String refreshTokenRedisKey) {
        LoginUser loginUser = redisUtils.get(refreshTokenRedisKey, LoginUser.class);
        //缓存信息为空，说明刷新令牌无效
        if (Objects.isNull(loginUser)) {
            throw new BusinessException(AUTH_INVALID_REFRESH_TOKEN.getCode(), AUTH_INVALID_REFRESH_TOKEN.getMessage());
        }

        //缓存信息存在，但是刷新令牌已过期
        if (DateUtils.isExpire(loginUser.getRefreshTokenExpireTime())) {
            //删除刷新令牌
            redisUtils.delete(refreshTokenRedisKey);
            redisUtils.delete(ACCESS_TOKEN.format(loginUser.getAccessToken()));
            throw new BusinessException(AUTH_REFRESH_TOKEN_ALREADY_EXPIRE.getCode(), AUTH_REFRESH_TOKEN_ALREADY_EXPIRE.getMessage());
        }

        return loginUser;
    }

    @Override
    public void logout(String token, LoginTypeEnum loginTypeEnum) {
        String accessTokenRedisKey = ACCESS_TOKEN.format(token);
        LoginUser loginUser = redisUtils.get(accessTokenRedisKey, LoginUser.class);
        //缓存信息不为null，执行删除缓存
        if (Objects.nonNull(loginUser)) {
            String refreshTokenRedisKey = REFRESH_TOKEN.format(loginUser.getRefreshToken());
            redisUtils.delete(accessTokenRedisKey);
            redisUtils.delete(refreshTokenRedisKey);
            //记录登出日志
            createLogoutLog(loginUser.getUserId(), loginUser.getUsername(), loginTypeEnum, UserTypeEnum.ADMIN_USER);
        }
    }

    /**
     * 构建AdminAuthLoginRespVO
     *
     * @param userId       用户id
     * @param status       状态
     * @param username     用户名
     * @param userTypeEnum 用户类型枚举
     * @return AdminAuthLoginRespVO
     */
    private AdminAuthLoginRespVO buildLoginResponseVO(Long userId, Integer status, String username, UserTypeEnum userTypeEnum) {
        //创建登录日志
        createLoginLog(userId, username, LoginTypeEnum.LOGIN_USERNAME, userTypeEnum, LoginResultEnum.SUCCESS);

        //构建LoginUser
        LoginUser loginUser = buildLoginUser(userId, username, userTypeEnum.getType(), status);

        //把令牌信息与用户信息放入缓存
        setTokenWithUserInfoCache(loginUser);

        //更新用户账号的登录ip与登录时间
        User user = buildUser(userId);
        userService.updateById(user);
        return AdminAuthConvert.INSTANCE.convert(loginUser);
    }

    /**
     * 把登录用户信息放入缓存
     *
     * @param loginUser 登录用户
     */
    private void setTokenWithUserInfoCache(LoginUser loginUser) {
        redisUtils.set(ACCESS_TOKEN.format(loginUser.getAccessToken()), JSONUtil.toJsonStr(loginUser), ACCESS_TOKEN.getExpireTime(), ACCESS_TOKEN.getTimeType());
        redisUtils.set(REFRESH_TOKEN.format(loginUser.getRefreshToken()), loginUser, REFRESH_TOKEN.getExpireTime(), REFRESH_TOKEN.getTimeType());
    }

    /**
     * 构建用户信息
     *
     * @param userId 用户id
     * @return User
     */
    private User buildUser(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setLoginIp(ServletUtils.getClientIp());
        user.setLoginDate(LocalDateTime.now());
        return user;
    }

    /**
     * 构建登录用户信息
     *
     * @param userId   用户id
     * @param userType 用户类型
     * @param status   状态
     * @return LoginUser
     */
    private LoginUser buildLoginUser(Long userId, String username, Integer userType,
                                     Integer status) {
        return LoginUser.builder()
                .userId(userId)
                .username(username)
                .userType(userType)
                .status(status)
                .accessToken(createAccessToken())
                .refreshToken(createRefreshToken())
                .accessTokenExpireTime(LocalDateTime.now().plusSeconds(securityProperties.getAccessTokenExpireTime()))
                .refreshTokenExpireTime(LocalDateTime.now().plusSeconds(securityProperties.getRefreshTokenExpireTime()))
                .build();
    }

    /**
     * 创建访问令牌
     *
     * @return accessToken
     */
    private String createRefreshToken() {
        return IdUtil.fastSimpleUUID();
    }

    /**
     * 创建刷新令牌
     *
     * @return refreshToken
     */
    private String createAccessToken() {
        return IdUtil.fastSimpleUUID();
    }

    /**
     * 用户认证，校验用户名密码、用户是否被禁用、用户类型等等
     *
     * @param adminAuthLoginReqVO 登录请求VO
     * @return user
     */
    public User authentication(AdminAuthLoginReqVO adminAuthLoginReqVO, UserTypeEnum userTypeEnum) {
        //根据用户名查询用户信息
        Optional<User> userOptional = userService.getUserByUsername(adminAuthLoginReqVO.getUsername());
        //判断用户是否存在
        User user = userOptional.orElseThrow(() -> {
            createLoginLog(null, adminAuthLoginReqVO.getUsername(), LoginTypeEnum.LOGIN_USERNAME, userTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            return new BusinessException(AUTH_BAD_CREDENTIALS.getCode(), AUTH_BAD_CREDENTIALS.getMessage());
        });
        //校验用户密码
        if (!userService.ifPasswordMatch(adminAuthLoginReqVO.getPassword(), user.getPassword())) {
            createLoginLog(user.getId(), user.getUsername(), LoginTypeEnum.LOGIN_USERNAME, userTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw new BusinessException(AUTH_BAD_CREDENTIALS.getCode(), AUTH_BAD_CREDENTIALS.getMessage());
        }
        //判断用户是否被禁用
        if (Objects.equals(user.getStatus(), CommonStatusEnum.DISABLED.getStatusCode())) {
            createLoginLog(user.getId(), user.getUsername(), LoginTypeEnum.LOGIN_USERNAME, userTypeEnum, LoginResultEnum.USER_DISABLED);
            throw new BusinessException(AUTH_USER_DISABLED.getCode(), AUTH_USER_DISABLED.getMessage());
        }

        //校验用户类型是否合法
        if (ObjUtil.notEqual(userTypeEnum.getType(), user.getType())) {
            createLoginLog(user.getId(), user.getUsername(), LoginTypeEnum.LOGIN_USERNAME, userTypeEnum, LoginResultEnum.USER_ILLEGAL_LOGIN);
            throw new BusinessException(AUTH_USER_ILLEGAL_LOGIN.getCode(), AUTH_USER_ILLEGAL_LOGIN.getMessage());
        }

        return user;
    }

    /**
     * 校验验证码
     *
     * @param adminAuthLoginReqVO 登录请求VO
     */
    public void verifyCaptcha(AdminAuthLoginReqVO adminAuthLoginReqVO) {
        //验证码未启用就不需要再继续验证了
        if (!captchaProperties.getEnabled()) {
            return;
        }

        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(adminAuthLoginReqVO.getCaptchaVerification());
        ResponseModel verificationResult = captchaService.verification(captchaVO);
        //验证不通过
        if (!verificationResult.isSuccess()) {
            //创建验证码校验失败的登录日志
            createLoginLog(null, adminAuthLoginReqVO.getUsername(), LoginTypeEnum.LOGIN_USERNAME, UserTypeEnum.ADMIN_USER, LoginResultEnum.CAPTCHA_ERROR);
            //抛出异常
            throw new BusinessException(AUTH_CAPTCHA_ERROR.getCode(), AUTH_CAPTCHA_ERROR.getMessage());
        }
    }

    /**
     * 创建登录日志
     *
     * @param userId          用户id
     * @param username        用户名
     * @param loginTypeEnum   登录类型枚举
     * @param userTypeEnum    用户类型枚举
     * @param loginResultEnum 登录结果枚举
     */
    private void createLoginLog(Long userId, String username, LoginTypeEnum loginTypeEnum, UserTypeEnum userTypeEnum, LoginResultEnum loginResultEnum) {
        //构建LoginLogCreateDTO
        LoginLogCreateDTO loginLogCreateDTO =
                new LoginLogCreateDTO(userId, username, loginTypeEnum.getType(),
                        userTypeEnum.getType(), loginResultEnum.getResult(),
                        ServletUtils.getUserAgent(), ServletUtils.getClientIp());
        //创建登录日志
        loginLogService.createLoginLog(loginLogCreateDTO);
    }

    /**
     * 创建登出日志
     * @param userId 用户id
     * @param username 用户名
     * @param loginTypeEnum 登录日志类型枚举
     * @param userTypeEnum 用户类型枚举
     */
    private void createLogoutLog(Long userId, String username, LoginTypeEnum loginTypeEnum, UserTypeEnum userTypeEnum){
        createLoginLog(userId, username, loginTypeEnum, userTypeEnum, LoginResultEnum.SUCCESS);
    }
}

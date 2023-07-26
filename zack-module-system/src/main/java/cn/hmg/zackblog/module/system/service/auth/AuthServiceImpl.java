package cn.hmg.zackblog.module.system.service.auth;

import cn.hmg.zackblog.common.enums.CommonStatusEnum;
import cn.hmg.zackblog.common.enums.UserTypeEnum;
import cn.hmg.zackblog.common.exception.ServiceException;
import cn.hmg.zackblog.common.exception.enums.GlobalErrorCode;
import cn.hmg.zackblog.common.utils.servlet.ServletUtils;
import cn.hmg.zackblog.framework.config.CaptchaProperties;
import cn.hmg.zackblog.framework.core.pojo.LoginUser;
import cn.hmg.zackblog.framework.core.utils.RedisUtils;
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

import static cn.hmg.zackblog.framework.core.constants.RedisKeyConstant.ACCESS_TOKEN;
import static cn.hmg.zackblog.framework.core.constants.RedisKeyConstant.REFRESH_TOKEN;
import static cn.hmg.zackblog.module.system.constants.CommonConstant.ACCESS_TOKEN_EXPIRE_TIME;
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
        String refreshTokenKey = REFRESH_TOKEN.format(refreshToken);
        //校验刷新令牌是否过期
        LoginUser loginUser = redisUtils.get(refreshTokenKey, LoginUser.class);

        if (Objects.isNull(loginUser)) {
            throw new ServiceException(GlobalErrorCode.UNAUTHORIZED.getCode(), GlobalErrorCode.UNAUTHORIZED.getMessage());
        }

        //删除刷新令牌
        redisUtils.delete(refreshTokenKey);

        //构建loginUser
        loginUser =  buildLoginUser(loginUser.getUserId(), loginUser.getUserType(), loginUser.getStatus());
        return AdminAuthConvert.INSTANCE.convert(loginUser);
    }

    /**
     * 构建AdminAuthLoginRespVO
     * @param userId 用户id
     * @param status 状态
     * @param username 用户名
     * @param userTypeEnum 用户类型枚举
     * @return AdminAuthLoginRespVO
     */
    private AdminAuthLoginRespVO buildLoginResponseVO(Long userId, Integer status, String username, UserTypeEnum userTypeEnum) {
        //创建登录日志
        createLoginLog(userId, username, LoginTypeEnum.LOGIN_USERNAME, userTypeEnum, LoginResultEnum.SUCCESS);

        //构建LoginUser，保存到Redis中
        LoginUser loginUser = buildLoginUser(userId, userTypeEnum.getType(), status);

        //更新用户账号的登录ip与登录时间
        User user = buildUser(userId);
        userService.updateById(user);
        return AdminAuthConvert.INSTANCE.convert(loginUser);
    }

    /**
     * 构建用户信息
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
     * @param userId 用户id
     * @param userType 用户类型
     * @param status 状态
     * @return LoginUser
     */
    private LoginUser buildLoginUser(Long userId, Integer userType, Integer status) {
        //创建访问令牌
        String accessToken = createAccessToken();
        //创建刷新令牌
        String refreshToken = createRefreshToken();

        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(userId);
        loginUser.setUserType(userType);
        loginUser.setStatus(status);
        loginUser.setAccessToken(accessToken);
        loginUser.setAccessTokenExpireTime(LocalDateTime.now().plusSeconds(ACCESS_TOKEN.getExpireTime()));
        loginUser.setRefreshToken(refreshToken);
        loginUser.setRefreshTokenExpireTime(LocalDateTime.now().plusSeconds(REFRESH_TOKEN.getExpireTime()));

        redisUtils.set(ACCESS_TOKEN.format(accessToken), JSONUtil.toJsonStr(loginUser), ACCESS_TOKEN.getExpireTime(), ACCESS_TOKEN.getTimeType());
        redisUtils.set(REFRESH_TOKEN.format(refreshToken), loginUser, REFRESH_TOKEN.getExpireTime(), REFRESH_TOKEN.getTimeType());
        return loginUser;
    }

    /**
     * 创建访问令牌
     * @return accessToken
     */
    private String createRefreshToken() {
        return IdUtil.fastSimpleUUID();
    }

    /**
     * 创建刷新令牌
     * @return refreshToken
     */
    private String createAccessToken() {
        return IdUtil.fastSimpleUUID();
    }

    /**
     * 用户认证，校验用户名密码、用户是否被禁用、用户类型等等
     * @param adminAuthLoginReqVO 登录请求VO
     * @return user
     */
    public User authentication(AdminAuthLoginReqVO adminAuthLoginReqVO, UserTypeEnum userTypeEnum) {
        //根据用户名查询用户信息
        Optional<User> userOptional = userService.getUserByUsername(adminAuthLoginReqVO.getUsername());
        //判断用户是否存在
        User user = userOptional.orElseThrow(() -> {
            createLoginLog(null, adminAuthLoginReqVO.getUsername(), LoginTypeEnum.LOGIN_USERNAME, userTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            return new ServiceException(AUTH_BAD_CREDENTIALS.getCode(), AUTH_BAD_CREDENTIALS.getMessage());
        });
        //校验用户密码
        if (!userService.ifPasswordMatch(adminAuthLoginReqVO.getPassword(), user.getPassword())) {
            createLoginLog(user.getId(), user.getUsername(), LoginTypeEnum.LOGIN_USERNAME, userTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw new ServiceException(AUTH_BAD_CREDENTIALS.getCode(), AUTH_BAD_CREDENTIALS.getMessage());
        }
        //判断用户是否被禁用
        if (Objects.equals(user.getStatus(), CommonStatusEnum.DISABLED.getStatusCode())) {
            createLoginLog(user.getId(), user.getUsername(), LoginTypeEnum.LOGIN_USERNAME, userTypeEnum, LoginResultEnum.USER_DISABLED);
            throw new ServiceException(AUTH_USER_DISABLED.getCode(), AUTH_USER_DISABLED.getMessage());
        }

        //校验用户类型是否合法
        if (ObjUtil.notEqual(userTypeEnum.getType(), user.getType())) {
            createLoginLog(user.getId(), user.getUsername(), LoginTypeEnum.LOGIN_USERNAME, userTypeEnum, LoginResultEnum.USER_ILLEGAL_LOGIN);
            throw new ServiceException(AUTH_USER_ILLEGAL_LOGIN.getCode(), AUTH_USER_ILLEGAL_LOGIN.getMessage());
        }

        return user;
    }

    /**
     * 校验验证码
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
            throw new ServiceException(AUTH_CAPTCHA_ERROR.getCode(), AUTH_CAPTCHA_ERROR.getMessage());
        }
    }

    /**
     * 创建登录日志
     * @param userId 用户id
     * @param username 用户名
     * @param loginTypeEnum 登录类型枚举
     * @param userTypeEnum 用户类型枚举
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
}

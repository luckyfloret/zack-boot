package cn.hmg.zackblog.module.system.service.auth;

import cn.hmg.zackblog.common.enums.CommonStatusEnum;
import cn.hmg.zackblog.common.enums.UserTypeEnum;
import cn.hmg.zackblog.common.exception.ServiceException;
import cn.hmg.zackblog.common.utils.servlet.ServletUtils;
import cn.hmg.zackblog.framework.config.CaptchaProperties;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.LoginReqVO;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.LoginRespVO;
import cn.hmg.zackblog.module.system.entity.user.User;
import cn.hmg.zackblog.module.system.enums.ErrorCodeEnum;
import cn.hmg.zackblog.module.system.enums.LoginResultEnum;
import cn.hmg.zackblog.module.system.enums.LoginTypeEnum;
import cn.hmg.zackblog.module.system.service.logger.ILoginLogService;
import cn.hmg.zackblog.module.system.service.logger.dto.LoginLogCreateDTO;
import cn.hmg.zackblog.module.system.service.user.IUserService;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

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

    @Override
    public LoginRespVO login(LoginReqVO loginReqVO) {
        //校验验证码
        verifyCaptcha(loginReqVO);

        //使用账号密码进行登录，进行一个用户认证
        User user = authentication(loginReqVO);

        //构建LoginRespVO并记录日志
        return null;
    }

    /**
     * 用户认证，校验用户名密码、用户是否被禁用等
     * @param loginReqVO 登录请求VO
     * @return user
     */
    public User authentication(LoginReqVO loginReqVO) {
        //根据用户名查询用户信息
        Optional<User> userOptional = userService.getUserByUsername(loginReqVO.getUsername());
        //判断用户是否存在
        User user = userOptional.orElseThrow(() -> {
            createLoginLog(null, loginReqVO.getUsername(), LoginTypeEnum.LOGIN_USERNAME, UserTypeEnum.ADMIN_USER, LoginResultEnum.BAD_CREDENTIALS);
            return new ServiceException(ErrorCodeEnum.AUTH_BAD_CREDENTIALS.getCode(), ErrorCodeEnum.AUTH_BAD_CREDENTIALS.getMessage());
        });
        //校验用户密码
        if (!userService.ifPasswordMatch(loginReqVO.getPassword(), user.getPassword())) {
            createLoginLog(user.getId(), user.getUsername(), LoginTypeEnum.LOGIN_USERNAME, UserTypeEnum.ADMIN_USER, LoginResultEnum.BAD_CREDENTIALS);
            throw new ServiceException(ErrorCodeEnum.AUTH_BAD_CREDENTIALS.getCode(), ErrorCodeEnum.AUTH_BAD_CREDENTIALS.getMessage());
        }
        //判断用户是否被禁用
        if (Objects.equals(user.getStatus(), CommonStatusEnum.DISABLED.getStatusCode())) {
            createLoginLog(user.getId(), user.getUsername(), LoginTypeEnum.LOGIN_USERNAME, UserTypeEnum.ADMIN_USER, LoginResultEnum.USER_DISABLED);
            throw new ServiceException(ErrorCodeEnum.AUTH_USER_DISABLED.getCode(), ErrorCodeEnum.AUTH_USER_DISABLED.getMessage());
        }
        return user;
    }

    /**
     * 校验验证码
     * @param loginReqVO 登录请求VO
     */
    public void verifyCaptcha(LoginReqVO loginReqVO) {
        //验证码未启用就不需要再继续验证了
        if (!captchaProperties.getEnabled()) {
            return;
        }

        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(loginReqVO.getCaptchaVerification());
        ResponseModel verificationResult = captchaService.verification(captchaVO);
        //验证不通过
        if (!verificationResult.isSuccess()) {
            //创建验证码校验失败的登录日志
            createLoginLog(null, loginReqVO.getUsername(), LoginTypeEnum.LOGIN_USERNAME, UserTypeEnum.ADMIN_USER, LoginResultEnum.CAPTCHA_ERROR);
            //抛出异常
            throw new ServiceException(ErrorCodeEnum.AUTH_CAPTCHA_ERROR.getCode(), ErrorCodeEnum.AUTH_CAPTCHA_ERROR.getMessage());
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

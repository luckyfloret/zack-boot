package cn.hmg.zackblog.module.system.service.auth;

import cn.hmg.zackblog.framework.config.CaptchaProperties;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.LoginReqVO;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.LoginRespVO;
import cn.hmg.zackblog.module.system.entity.user.User;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-12 17:02
 * @description: 认证服务实现类
 */
@Service
public class AuthServiceImpl implements AuthService{

    @Resource
    private CaptchaService captchaService;

    @Value("${zack.captcha.enabled}")
    private Boolean enabled;

    @Override
    public LoginRespVO login(LoginReqVO loginReqVO) {
        //校验验证码
        verifyCaptcha(loginReqVO);

        //使用账号密码进行登录，进行一个用户认证
        User user = authentication(loginReqVO);

        //构建LoginRespVO并记录日志
        return null;
    }

    private User authentication(LoginReqVO loginReqVO) {
        return null;
    }

    private void verifyCaptcha(LoginReqVO loginReqVO) {
        //验证码未启用就不需要再继续验证了
        if (!enabled) {
            return;
        }

        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(loginReqVO.getCaptchaVerification());
        ResponseModel verificationResult = captchaService.verification(captchaVO);
        //验证不通过
        if (!verificationResult.isSuccess()) {
            //创建验证码校验失败的登录日志

            //抛出异常
        }
    }
}

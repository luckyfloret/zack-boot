package cn.hmg.zackblog.module.system.service.auth;

import cn.hmg.zackblog.module.system.controller.admin.auth.vo.LoginReqVO;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.LoginRespVO;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-12 16:51
 * @description: 认证服务
 */
public interface AuthService {
    /**
     * 账号密码登录
     * @param loginReqVO 请求参数
     * @return LoginRespVO
     */
    LoginRespVO login(LoginReqVO loginReqVO);
}

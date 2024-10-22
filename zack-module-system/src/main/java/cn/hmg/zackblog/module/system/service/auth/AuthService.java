package cn.hmg.zackblog.module.system.service.auth;

import cn.hmg.zackblog.framework.common.enums.UserTypeEnum;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.AdminAuthLoginReqVO;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.AdminAuthLoginRespVO;
import cn.hmg.zackblog.module.system.enums.LoginTypeEnum;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-12 16:51
 * @description: 认证服务
 */
public interface AuthService {
    /**
     * 账号密码登录
     *
     * @param adminAuthLoginReqVO 请求参数
     * @param userTypeEnum 用户类型枚举，用于区分前后台用户
     * @return AdminAuthLoginRespVO
     */
    AdminAuthLoginRespVO login(AdminAuthLoginReqVO adminAuthLoginReqVO, UserTypeEnum userTypeEnum);

    /**
     * 根据刷新令牌重新生成访问令牌与刷新令牌
     * @param refreshToken 刷新令牌
     * @return AdminAuthLoginRespVO
     */
    AdminAuthLoginRespVO refreshToken(String refreshToken);

    /**
     * 用户登出
     * @param token 访问令牌
     * @param loginTypeEnum 日志类型
     */
    void logout(String token, LoginTypeEnum loginTypeEnum);
}

package cn.hmg.zackblog.framework.core.pojo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-10 16:26
 * @description:  用户实体
 */
@Data
public class LoginUser {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户类型 => 前台用户 or 后台用户
     */
    private Integer userType;

    /**
     * 用户状态 => 启用 or 禁用
     */
    private Integer status;

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 访问令牌的过期时间
     */
    private LocalDateTime accessTokenExpireTime;

    /**
     * 刷新令牌，刷新令牌的过期时间为redis key timeout时间，如redis key过期，即refresh_token过期
     */
    private String refreshToken;
}

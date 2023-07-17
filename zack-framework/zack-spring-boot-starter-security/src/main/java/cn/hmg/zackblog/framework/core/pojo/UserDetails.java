package cn.hmg.zackblog.framework.core.pojo;

import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-17 0:11
 * @description: 用户详情信息
 */
@Data
public class UserDetails {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态
     */
    private Integer status;
}

package cn.hmg.zackblog.module.system.service.logger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-14 11:02
 * @description: 创建登录日志DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginLogCreateDTO {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录类型
     */
    private Integer loginType;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 登录结果
     */
    private Integer result;

    /**
     * 浏览器ua
     */
    private String userAgent;

    /**
     * 用户ip
     */
    private String userIp;
}

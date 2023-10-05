package cn.hmg.zackblog.framework.mail.core.client;

import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-18 15:06
 * @description: SMTP配置类
 */
@Data
public class SMTPConfig {
    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户名
     */
    private String username;

    /**
     * 授权码
     */
    private String password;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 主机地址
     */
    private String host;

    /**
     * 是否开启ssl
     */
    private boolean sslEnable;
}

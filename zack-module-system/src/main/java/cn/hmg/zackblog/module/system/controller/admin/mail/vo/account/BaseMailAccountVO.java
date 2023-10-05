package cn.hmg.zackblog.module.system.controller.admin.mail.vo.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-22 19:12
 * @description: 基础邮箱账户vo
 */
@Data
@Schema(name = "基础邮箱账户vo")
public class BaseMailAccountVO {
    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "SMTP端口")
    private String port;

    @Schema(description = "SMTP域名")
    private String host;

    @Schema(description = "是否开启ssl （0 false ，1 true）， 默认开启")
    private Boolean sslEnable;
}

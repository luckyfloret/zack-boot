package cn.hmg.zackblog.module.system.controller.admin.mail.vo.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-10-05 14:23
 * @description: 邮箱账号简单的列表 response vo
 */
@Data
@Schema(name = "邮箱账号简单的列表 response vo")
public class MailAccountListSimpleRespVO {
    @Schema(description = "邮箱账号id")
    private Long id;

    @Schema(description = "邮箱账号")
    private String email;
}

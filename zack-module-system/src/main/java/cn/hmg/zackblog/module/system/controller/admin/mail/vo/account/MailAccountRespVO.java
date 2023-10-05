package cn.hmg.zackblog.module.system.controller.admin.mail.vo.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-22 19:13
 * @description: 邮箱账户 response vo
 */
@Data
@Schema(name = "邮箱账户 response vo")
public class MailAccountRespVO extends BaseMailAccountVO{
    @Schema(description = "id （主键）")
    private Long id;
}

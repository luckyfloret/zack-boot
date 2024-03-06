package cn.hmg.zackblog.module.system.controller.admin.mail.vo.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-22 19:13
 * @description: 更新邮箱账户 request vo
 */
@Data
@Schema(name = "更新邮箱账户 request vo")
public class MailAccountUpdateReqVO extends BaseMailAccountVO{
    @Schema(description = "id （主键）")
    private Long id;
}

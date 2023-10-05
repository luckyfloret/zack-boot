package cn.hmg.zackblog.module.system.controller.admin.mail.vo.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-22 19:19
 * @description: 更新邮件模板request vo
 */
@Data
@Schema(name = "更新邮件模板request vo")
public class MailTemplateUpdateReqVO extends BaseMailTemplateVO{
    @Schema(description = "id （主键）")
    private Long id;
}

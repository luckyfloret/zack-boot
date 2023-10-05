package cn.hmg.zackblog.module.system.controller.admin.mail.vo.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-22 19:18
 * @description: 邮件模板 response vo
 */
@Data
@Schema(name = "邮件模板 response vo")
public class MailTemplateRespVO extends BaseMailTemplateVO{
    @Schema(description = "id （主键）")
    private Long id;

    @Schema(description = "模板参数")
    private List<String> params;
}

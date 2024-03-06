package cn.hmg.zackblog.module.system.controller.admin.mail.vo.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-10-04 11:00
 * @description: 邮件模板测试发送 request vo
 */
@Data
@Schema(name = "邮件模板测试发送 request vo")
public class MailTemplateSendReqVO {
    @Schema(description = "收件人")
    @NotBlank(message = "收件人邮箱不能为空")
    @Email(message = "请输入正确的邮箱格式")
    private String toMail;

    @Schema(description = "模板编码")
    @NotBlank(message = "模板编码不能为空")
    private String templateCode;

    @Schema(description = "模板参数")
    private Map<String, Object> templateParams;
}

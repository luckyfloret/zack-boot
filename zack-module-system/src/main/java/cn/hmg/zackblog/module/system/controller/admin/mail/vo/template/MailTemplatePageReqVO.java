package cn.hmg.zackblog.module.system.controller.admin.mail.vo.template;

import cn.hmg.zackblog.framework.common.pojo.PageQueryParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-22 19:18
 * @description: 邮件模板分页 request vo
 */
@Data
@Schema(name = "邮件模板分页 request vo")
public class MailTemplatePageReqVO extends PageQueryParam {
    @Schema(description = "模板名称")
    private String name;

    @Schema(description = "邮箱账户")
    private Long accountId;

    @Schema(description = "模板编码")
    private String code;
    @Schema(description = "状态（0 关闭，1 开启 ）")
    private Integer status;
}

package cn.hmg.zackblog.module.system.controller.admin.mail.vo.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-22 19:17
 * @description: 基础邮箱模板 vo
 */
@Data
@Schema(name = "基础邮箱模板 vo")
public class BaseMailTemplateVO {
    @Schema(description = "模板编码")
    private String code;

    @Schema(description = "模板名称")
    private String name;

    @Schema(description = "用户名称")
    private String nickname;

    @Schema(description = "邮箱账户")
    private Long accountId;

    @Schema(description = "模板标题")
    private String title;

    @Schema(description = "模板内容")
    private String content;

    @Schema(description = "状态（0 开启，1 禁用 ）")
    private Integer status;

    @Schema(description = "备注")
    private String remark;
}

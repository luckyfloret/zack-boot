package cn.hmg.zackblog.module.website.controller.admin.vo.notice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-31 23:37
 * @description: 基础通知公告 vo
 */
@Data
@Schema(name = "基础通知公告 vo")
public class BaseNoticeVO {
    @Schema(description = "标题")
    @NotBlank(message = "标题不能为空")
    private String title;

    @Schema(description = "内容")
    @NotBlank(message = "内容不能为空")
    private String content;
}

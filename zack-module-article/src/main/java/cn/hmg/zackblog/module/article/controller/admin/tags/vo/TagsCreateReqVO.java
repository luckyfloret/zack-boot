package cn.hmg.zackblog.module.article.controller.admin.tags.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-16 23:54
 * @description: 标签创建 request vo
 */
@Data
@Schema(name = "标签创建 request vo")
public class TagsCreateReqVO {
    @Schema(description = "标签名")
    @NotBlank(message = "标签名称不能为空")
    private String tagName;

    @Schema(description = "排序")
    @NotNull(message = "排序不能为空")
    private Integer sort;
}

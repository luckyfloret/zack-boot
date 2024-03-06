package cn.hmg.zackblog.module.article.controller.admin.tags.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-16 23:53
 * @description: 基础Tags vo
 */
@Data
@Schema(name = "基础Tags vo")
public class BaseTagsVO {
    @Schema(description = "id 主键")
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "标签名")
    @NotBlank(message = "标签名称不能为空")
    private String tagName;

    @Schema(description = "排序")
    @NotNull(message = "排序不能为空")
    private Integer sort;
}

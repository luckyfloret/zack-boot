package cn.hmg.zackblog.module.article.controller.admin.category.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-16 21:09
 * @description: 分类创建 request vo
 */
@Data
@Schema(name = "分类创建 request vo")
public class CategoryCreateReqVO {
    @Schema(description = "分类名称")
    @NotBlank(message = "分类名称不能为空")
    private String categoryName;

    @Schema(description = "排序")
    @NotNull(message = "排序不能为空")
    private Integer sort;
}

package cn.hmg.zackblog.module.article.controller.admin.category.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-16 20:57
 * @description: 基础分类 vo
 */
@Data
@Schema(name = "基础分类 vo")
public class BaseCategoryVO {
    @Schema(description = "id  主键")
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "分类名称")
    @NotBlank(message = "分类名称不能为空")
    private String categoryName;

    @Schema(description = "排序")
    @NotNull(message = "排序不能为空")
    private Integer sort;
}

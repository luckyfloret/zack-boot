package cn.hmg.zackblog.module.article.controller.admin.article.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-13 13:27
 * @description: 更新文章 request vo
 */
@Schema(name = "更新文章 request vo")
@Data
public class ArticleUpdateReqVO extends BaseArticleVO{
    @Schema(description = "文章id")
    @NotNull(message = "文章id不能为空")
    private Long id;

    @Schema(description = "分类id")
    @NotNull(message = "分类不能为空")
    private Long categoryId;

    @Schema(description = "标签ids")
    private Set<Long> tagIds;
}

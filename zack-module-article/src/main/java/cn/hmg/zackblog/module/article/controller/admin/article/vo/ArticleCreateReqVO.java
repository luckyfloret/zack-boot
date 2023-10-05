package cn.hmg.zackblog.module.article.controller.admin.article.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-13 13:27
 * @description: 创建文章 request vo
 */
@Data
@Schema(name = "创建文章 request vo")
public class ArticleCreateReqVO extends BaseArticleVO {
    @Schema(description = "分类id")
    private Long categoryId;

    @Schema(description = "标签ids")
    private Set<Long> tagIds;
}

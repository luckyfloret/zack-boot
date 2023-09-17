package cn.hmg.zackblog.module.article.controller.admin.article.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-13 13:28
 * @description: 文章 response vo
 */
@Data
@Schema(name = "文章 response vo")
public class ArticleRespVO extends BaseArticleVO{
    @Schema(description = "文章id")
    private Long id;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "标签列表")
    private List<ArticlePageRespVO.TagVO> tagVOList;
}

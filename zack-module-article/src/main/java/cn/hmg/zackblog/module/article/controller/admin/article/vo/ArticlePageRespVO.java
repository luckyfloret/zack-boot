package cn.hmg.zackblog.module.article.controller.admin.article.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-13 13:26
 * @description: 文章分页 response vo
 */
@Data
@Schema(name = "文章分页 response vo")
public class ArticlePageRespVO {
    @Schema(description = "文章id")
    private Long id;

    @Schema(description = "文章封面")
    private String articleCover;

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "类型 （1 原创、2 转载）")
    private Integer type;

    @Schema(description = "是否推荐文章")
    private Integer isRecommend;

    @Schema(description = "是否发布（1 发布，2 下架）")
    private Integer isPublish;

    @Schema(description = "阅读量")
    private String readingQuantity;

    @Schema(description = "分类id")
    private Long categoryId;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "标签列表")
    private List<TagVO> tagVOList;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Data
    public static class TagVO {
        @Schema(description = "id")
        private Long id;

        @Schema(description = "标签名称")
        private String tagName;
    }
}

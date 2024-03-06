package cn.hmg.zackblog.module.article.controller.admin.article.vo;

import cn.hmg.zackblog.framework.common.pojo.PageQueryParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-13 13:26
 * @description: 文章分页 request vo
 */
@Data
@Schema(name = "文章分页 request vo")
public class ArticlePageReqVO extends PageQueryParam {
    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "标签id")
    private Long tagId;

    @Schema(description = "分类id")
    private Long categoryId;

    @Schema(description = "状态")
    private Integer isPublish;
}

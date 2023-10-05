package cn.hmg.zackblog.module.article.controller.admin.article.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-13 12:25
 * @description: 基础文章 vo
 */
@Data
@Schema(name = "基础文章 vo")
public class BaseArticleVO {
    @Schema(description = "文章封面")
    private String articleCover;

    @Schema(description = "文章标题")
    @NotBlank(message = "标题不能为空")
    private String title;

    @Schema(description = "转载地址")
    private String reprintUrl;

    @Schema(description = "简介")
    private String summary;

    @Schema(description = "内容-markdown")
    private String contentMd;

    @Schema(description = "内容-html")
    private String contentHtml;

    @Schema(description = "类型 （1 原创、2 转载）")
    @NotNull(message = "类型不能为空")
    private Integer type;

    @Schema(description = "是否推荐文章")
    @NotNull(message = "请选择是否推荐文章")
    private Integer isRecommend;

    @Schema(description = "是否发布（1 发布，2 下架）")
    @NotNull(message = "发布状态不能为空")
    private Integer isPublish;

    @Schema(description = "关键词（作为保留字段，主要用于seo）")
    private String keywords;

    @Schema(description = "备注")
    private String remark;
}

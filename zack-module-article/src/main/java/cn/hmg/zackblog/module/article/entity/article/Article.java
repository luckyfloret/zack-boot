package cn.hmg.zackblog.module.article.entity.article;

import cn.hmg.zackblog.framework.mybatisplus.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * <p>
 * 文章管理
 * </p>
 *
 * @author hmg
 * @since 2023-09-11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("biz_article")
@Schema(name = "Article对象", description = "文章管理")
public class Article extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id (主键)")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "文章封面")
    private String articleCover;

    @Schema(description = "文章名字")
    private String title;

    @Schema(description = "类型 （1 原创、2 转载）")
    private Integer type;

    @Schema(description = "分类id")
    private Long categoryId;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "简介")
    private String summary;

    @Schema(description = "内容-markdown")
    private String contentMd;

    @Schema(description = "内容-html")
    private String contentHtml;

    @Schema(description = "是否推荐文章")
    private Integer isRecommend;

    @Schema(description = "是否发布（1 发布，2 下架）")
    private Integer isPublish;

    @Schema(description = "是否私密 （1 私密、2 公开）")
    private Integer isSecret;

    @Schema(description = "转载地址")
    private String reprintUrl;

    @Schema(description = "阅读量")
    private String readingQuantity;

    @Schema(description = "关键词（作为保留字段，主要用于seo）")
    private String keywords;

    @Schema(description = "备注")
    private String remark;
}

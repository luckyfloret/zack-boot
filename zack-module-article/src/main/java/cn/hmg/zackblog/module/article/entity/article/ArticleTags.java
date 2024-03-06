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
 * 文章标签中间表
 * </p>
 *
 * @author hmg
 * @since 2023-09-11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("biz_article_tags")
@Schema(name = "ArticleTags对象", description = "文章标签中间表")
public class ArticleTags extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id 主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "文章id")
    private Long articleId;

    @Schema(description = "标签id")
    private Long tagId;
}

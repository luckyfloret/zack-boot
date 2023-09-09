package cn.hmg.zackblog.module.website.entity;

import cn.hmg.zackblog.framework.mybatisplus.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 评论管理
 * </p>
 *
 * @author hmg
 * @since 2023-08-30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("biz_comment")
@Schema(name = "Comment对象", description = "评论管理")
public class Comment extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Long ROOT = 0L;

    @Schema(description = "id 主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "文章id")
    private Long articleId;

    @Schema(description = "父级id")
    private Long parentId;

    @Schema(description = "回复人id")
    private Long replyUserId;

    @Schema(description = "评论内容")
    private String content;
}

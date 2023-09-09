package cn.hmg.zackblog.module.website.controller.admin.vo.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-03 13:57
 * @description: 评论 response vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "评论 response vo")
public class CommentRespVO extends BaseCommentVO{
    @Schema(description = "父级id")
    private Long parentId;

    @Schema(description = "评论内容")
    private String content;
}

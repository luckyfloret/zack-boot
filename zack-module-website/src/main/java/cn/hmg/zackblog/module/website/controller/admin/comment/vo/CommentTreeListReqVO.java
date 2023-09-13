package cn.hmg.zackblog.module.website.controller.admin.comment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-03 13:57
 * @description: 评论列表 request vo
 */
@Data
@Schema(name = "评论列表 request vo")
public class CommentTreeListReqVO {
    @Schema(description = "文章id")
    private Long articleId;
}

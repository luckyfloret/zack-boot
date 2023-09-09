package cn.hmg.zackblog.module.website.controller.admin.vo.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-03 11:55
 * @description: 基础评论vo
 */
@Data
@Schema(name = "基础评论vo")
public class BaseCommentVO {
    @Schema(description = "id")
    private Long id;

    @Schema(description = "文章名称")
    private String articleName;

    @Schema(description = "评论人")
    private String username;

    @Schema(description = "回复人")
    private String replyUsername;

    @Schema(description = "父级评论")
    private Long parentId;
}

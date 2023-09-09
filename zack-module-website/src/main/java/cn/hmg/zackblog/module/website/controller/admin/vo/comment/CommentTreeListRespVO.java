package cn.hmg.zackblog.module.website.controller.admin.vo.comment;

import lombok.Data;

import java.util.List;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-03 11:58
 * @description:
 */
@Data
public class CommentTreeListRespVO extends BaseCommentVO {
    private List<CommentTreeListRespVO> children;
}

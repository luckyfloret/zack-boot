package cn.hmg.zackblog.module.website.service.comment;

import cn.hmg.zackblog.module.website.controller.admin.comment.vo.CommentTreeListReqVO;
import cn.hmg.zackblog.module.website.entity.comment.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 评论管理 服务类
 * </p>
 *
 * @author hmg
 * @since 2023-08-30
 */
public interface ICommentService extends IService<Comment> {

    List<Comment> getList(CommentTreeListReqVO reqVO);

    Comment getDetailsById(Long id);

    void deleteCommentById(Long id);
}

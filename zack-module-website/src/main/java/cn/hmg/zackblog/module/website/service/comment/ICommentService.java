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
    /**
     * 获取评论列表
     * @param reqVO 请求参数
     * @return 评论列表
     */
    List<Comment> getList(CommentTreeListReqVO reqVO);

    /**
     * 根据id获取评论详情
     * @param id 评论id
     * @return comment
     */
    Comment getDetailsById(Long id);

    /**
     * 根据id删除评论
     * @param id 评论id
     */
    void deleteCommentById(Long id);
}

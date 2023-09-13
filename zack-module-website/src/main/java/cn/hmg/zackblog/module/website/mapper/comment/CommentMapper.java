package cn.hmg.zackblog.module.website.mapper.comment;

import cn.hmg.zackblog.framework.mybatisplus.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.framework.mybatisplus.core.query.LambdaQueryWrapperExtend;
import cn.hmg.zackblog.module.website.controller.admin.comment.vo.CommentTreeListReqVO;
import cn.hmg.zackblog.module.website.entity.comment.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 评论管理 Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-08-30
 */
@Mapper
public interface CommentMapper extends BaseMapperExtend<Comment> {
    default List<Comment> selectList(CommentTreeListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperExtend<Comment>()
                .eqIfExists(Comment::getArticleId, reqVO.getArticleId())
        );
    }

    default Long selectCountByParentId(Long parentId) {
        return selectCount(Comment::getParentId, parentId);
    }

    default void deleteByParentId(Long parentId) {
        delete(Comment::getParentId, parentId);
    }
}

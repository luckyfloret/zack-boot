package cn.hmg.zackblog.module.website.service.comment;

import cn.hmg.zackblog.framework.common.exception.BusinessException;
import cn.hmg.zackblog.module.website.controller.admin.comment.vo.CommentTreeListReqVO;
import cn.hmg.zackblog.module.website.entity.comment.Comment;
import cn.hmg.zackblog.module.website.mapper.comment.CommentMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static cn.hmg.zackblog.module.website.enums.ErrorCodeEnum.COMMENT_NOT_EXISTS;

/**
 * <p>
 * 评论管理 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-08-30
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public List<Comment> getList(CommentTreeListReqVO reqVO) {
        //TODO 组装数据
        return commentMapper.selectList(reqVO);
    }

    @Override
    public Comment getDetailsById(Long id) {
        return verifyCommentIsExists(id);
    }

    @Override
    public void deleteCommentById(Long id) {
        //校验评论是否存在
        verifyCommentIsExists(id);

        //判断子级是否存在
        if (commentMapper.selectCountByParentId(id) > 0) {
            commentMapper.deleteByParentId(id);
        }

        //执行删除
        commentMapper.deleteById(id);
    }

    /**
     * 校验评论是否存在
     * @param id 评论id
     * @return comment
     */
    private Comment verifyCommentIsExists(Long id) {
        Comment comment = commentMapper.selectById(id);
        if (Objects.isNull(comment)) {
            throw new BusinessException(COMMENT_NOT_EXISTS.getCode(), COMMENT_NOT_EXISTS.getMessage());
        }

        return comment;
    }
}

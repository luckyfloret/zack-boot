package cn.hmg.zackblog.module.website.convert.admin.comment;

import cn.hmg.zackblog.module.website.controller.admin.vo.comment.CommentTreeListRespVO;
import cn.hmg.zackblog.module.website.controller.admin.vo.comment.CommentRespVO;
import cn.hmg.zackblog.module.website.entity.Comment;
import cn.hutool.core.util.ObjUtil;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

import static cn.hmg.zackblog.module.website.entity.Comment.ROOT;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-03 14:03
 * @description:
 */
@Mapper
public interface CommentConvert {
    CommentConvert INSTANCE = Mappers.getMapper(CommentConvert.class);


    CommentTreeListRespVO convertCommentListRespVO(Comment comment);

    default List<CommentTreeListRespVO> buildCommentTree(List<Comment> comments) {
        //构建Map
        Map<Long, CommentTreeListRespVO> nodeMap =
                comments.stream().map(this::convertCommentListRespVO).collect(Collectors.toMap(CommentTreeListRespVO::getId, commentTreeListRespVO -> commentTreeListRespVO));

        comments.stream().filter(node -> ObjUtil.notEqual(ROOT, node.getParentId())).forEach(node -> {
            Long parentId = node.getParentId();
            CommentTreeListRespVO parentNode = nodeMap.get(parentId);
            CommentTreeListRespVO childrenNode = nodeMap.get(node.getId());
            if (Objects.isNull(parentNode)) {
                LoggerFactory.getLogger(getClass()).error("找不到父节点， parent id => {}, children id => {}", parentId, childrenNode.getId());
                return;
            }

            if (Objects.isNull(parentNode.getChildren())) {
                parentNode.setChildren(new ArrayList<>());
            }

            //将子节点添加到父节点
            parentNode.getChildren().add(childrenNode);
        });

        //最后取出所有根节点
        return nodeMap.values().stream().filter(node -> ROOT.equals(node.getParentId())).collect(Collectors.toList());
    }

    CommentRespVO convert(Comment comment);
}

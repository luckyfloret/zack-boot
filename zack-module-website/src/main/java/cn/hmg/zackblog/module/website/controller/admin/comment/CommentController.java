package cn.hmg.zackblog.module.website.controller.admin.comment;

import cn.hmg.zackblog.framework.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.operatelog.core.annotation.OperateLog;
import cn.hmg.zackblog.module.website.controller.admin.comment.vo.CommentTreeListReqVO;
import cn.hmg.zackblog.module.website.controller.admin.comment.vo.CommentTreeListRespVO;
import cn.hmg.zackblog.module.website.controller.admin.comment.vo.CommentRespVO;
import cn.hmg.zackblog.module.website.convert.admin.comment.CommentConvert;
import cn.hmg.zackblog.module.website.service.comment.ICommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.hmg.zackblog.framework.common.pojo.CommonResult.success;
import static cn.hmg.zackblog.framework.operatelog.core.enums.OperateLogTypeEnum.DELETE;
import static cn.hmg.zackblog.framework.operatelog.core.enums.OperateLogTypeEnum.QUERY;

/**
 * <p>
 * 评论管理 前端控制器
 * </p>
 *
 * @author hmg
 * @since 2023-08-30
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "后台-评论管理")
@RequestMapping("/admin/website/comment")
public class CommentController {
    private final ICommentService commentService;


    @GetMapping("/list")
    @PreAuthorize("@spe.hasPermission('website:comment:list')")
    @Operation(summary = "评论列表")
    @OperateLog(operateName = "评论列表", operateType = QUERY)
    public CommonResult<List<CommentTreeListRespVO>> list(CommentTreeListReqVO reqVO) {
        return success(CommentConvert.INSTANCE.buildCommentTree(commentService.getList(reqVO)));
    }

    @GetMapping("/get-details/{id}")
    @PreAuthorize("@spe.hasPermission('website:comment:query')")
    @Operation(summary = "根据id获取评论详情")
    @OperateLog(operateName = "根据id获取评论详情", operateType = QUERY)
    public CommonResult<CommentRespVO> getDetailsById(@PathVariable("id") Long id) {
        return success(CommentConvert.INSTANCE.convert(commentService.getDetailsById(id)));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("@spe.hasPermission('website:comment:delete')")
    @Operation(summary = "删除评论")
    @OperateLog(operateName = "删除评论", operateType = DELETE)
    public CommonResult<Boolean> deleteCommentById(@PathVariable("id") Long id) {
        commentService.deleteCommentById(id);
        return success(true);
    }
}

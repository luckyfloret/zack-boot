package cn.hmg.zackblog.module.website.controller.admin;

import cn.hmg.zackblog.framework.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.operatelog.core.annotation.OperateLog;
import cn.hmg.zackblog.module.website.controller.admin.vo.notice.*;
import cn.hmg.zackblog.module.website.convert.admin.notice.NoticeConvert;
import cn.hmg.zackblog.module.website.service.INoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static cn.hmg.zackblog.framework.common.pojo.CommonResult.success;
import static cn.hmg.zackblog.framework.operatelog.core.enums.OperateLogTypeEnum.*;

/**
 * <p>
 * 消息管理 前端控制器
 * </p>
 *
 * @author hmg
 * @since 2023-08-30
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "后台-通知公告")
@RequestMapping("/admin/website/notice")
public class NoticeController {
    private final INoticeService noticeService;

    @GetMapping("/page")
    @PreAuthorize("@spe.hasPermission('website:notice:list')")
    @Operation(summary = "通知公告分页列表")
    @OperateLog(operateName = "通知公告分页列表", operateType = QUERY)
    public CommonResult<PageResult<NoticePageRespVO>> page(@Valid NoticePageReqVO reqVO){
        return success(NoticeConvert.INSTANCE.convert(noticeService.getPage(reqVO)));
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("@spe.hasPermission('website:notice:query')")
    @Operation(summary = "根据id获取通知公告")
    @OperateLog(operateName = "根据id获取通知公告", operateType = QUERY)
    public CommonResult<NoticeRespVO> getNoticeById(@PathVariable("id") Long id){
        return success(NoticeConvert.INSTANCE.convert(noticeService.getNoticeById(id)));
    }

    @PostMapping("/create")
    @PreAuthorize("@spe.hasPermission('website:notice:create')")
    @Operation(summary = "创建通知公告")
    @OperateLog(operateName = "创建通知公告", operateType = CREATE)
    public CommonResult<Boolean> createNotice(@Valid @RequestBody NoticeCreateReqVO reqVO){
        noticeService.createNotice(reqVO);
        return success(true);
    }

    @PutMapping("/update")
    @PreAuthorize("@spe.hasPermission('website:notice:update')")
    @Operation(summary = "更新通知公告")
    @OperateLog(operateName = "更新通知公告", operateType = UPDATE)
    public CommonResult<Boolean> updateNotice(@Valid @RequestBody NoticeUpdateReqVO reqVO){
        noticeService.updateNotice(reqVO);
        return success(true);
    }

    @PutMapping("/update-status")
    @PreAuthorize("@spe.hasPermission('website:notice:update-status')")
    @Operation(summary = "更新通知公告状态")
    @OperateLog(operateName = "更新通知公告状态", operateType = UPDATE)
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody NoticeUpdateStatusReqVO reqVO) {
        noticeService.updateStatus(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("@spe.hasPermission('website:notice:delete')")
    @Operation(summary = "删除通知公告")
    @OperateLog(operateName = "删除通知公告", operateType = DELETE)
    public CommonResult<Boolean> deleteNoticeById(@PathVariable("id") Long id){
        noticeService.deleteNoticeById(id);
        return success(true);
    }


}

package cn.hmg.zackblog.module.website.controller.admin;

import cn.hmg.zackblog.framework.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.operatelog.core.annotation.OperateLog;
import cn.hmg.zackblog.module.website.controller.admin.vo.friendlink.*;
import cn.hmg.zackblog.module.website.convert.admin.friendlink.FriendLinkConvert;
import cn.hmg.zackblog.module.website.service.IFriendLinkService;
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
 * 友情链接 前端控制器
 * </p>
 *
 * @author hmg
 * @since 2023-08-30
 */
@RestController
@Tag(name = "后台-友情链接")
@RequiredArgsConstructor
@RequestMapping("/admin/website/friend-link")
public class FriendLinkController {
    private final IFriendLinkService friendLinkService;

    @GetMapping("/page")
    @PreAuthorize("@spe.hasPermission('website:friend-link:list')")
    @Operation(summary = "友情链接分页列表")
    @OperateLog(operateName = "友情链接分页列表", operateType = QUERY)
    public CommonResult<PageResult<FriendLinkPageRespVO>> page(@Valid FriendLinkPageReqVO reqVO) {
        return success(FriendLinkConvert.INSTANCE.convert(friendLinkService.getPage(reqVO)));
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("@spe.hasPermission('website:friend-link:query')")
    @Operation(summary = "根据id获取友情链接")
    @OperateLog(operateName = "根据id获取友情链接", operateType = QUERY)
    public CommonResult<FriendLinkRespVO> getFriendLinkById(@PathVariable("id") Long id) {
        return success(FriendLinkConvert.INSTANCE.convert(friendLinkService.getFriendLinkById(id)));
    }

    @PostMapping("/create")
    @PreAuthorize("@spe.hasPermission('website:friend-link:create')")
    @Operation(summary = "创建友情链接")
    @OperateLog(operateName = "创建友情链接", operateType = CREATE)
    public CommonResult<Boolean> createFriendLink(@Valid @RequestBody FriendLinkCreateReqVO reqVO) {
        friendLinkService.createFriendLink(reqVO);
        return success(true);
    }

    @PutMapping("/update")
    @PreAuthorize("@spe.hasPermission('website:friend-link:update')")
    @Operation(summary = "更新友情链接")
    @OperateLog(operateName = "更新友情链接", operateType = UPDATE)
    public CommonResult<Boolean> updateFriendLink(@Valid @RequestBody FriendLinkUpdateReqVO reqVO) {
        friendLinkService.updateFriendLink(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("@spe.hasPermission('website:friend-link:delete')")
    @Operation(summary = "根据id删除友情链接")
    @OperateLog(operateName = "根据id删除友情链接", operateType = DELETE)
    public CommonResult<Boolean> deleteFriendLinkById(@PathVariable("id") Long id) {
        friendLinkService.deleteFriendLinkById(id);
        return success(true);
    }

    @PutMapping("/approval-pass-friend-link/{id}")
    @PreAuthorize("@spe.hasPermission('website:friend-link:approval')")
    @Operation(summary = "审批通过")
    @OperateLog(operateName = "审批通过", operateType = UPDATE)
    public CommonResult<Boolean> approvalPassFriendLink(@PathVariable("id") Long id) {
        friendLinkService.approvalPassFriendLink(id);
        return success(true);
    }

    @PutMapping("/approval-reject-friend-link")
    @PreAuthorize("@spe.hasPermission('website:friend-link:approval')")
    @Operation(summary = "审批驳回")
    @OperateLog(operateName = "审批驳回", operateType = UPDATE)
    public CommonResult<Boolean> approvalRejectFriendLink(@Valid @RequestBody FriendLinkApprovalRejectReqVO reqVO) {
        friendLinkService.approvalRejectFriendLink(reqVO);
        return success(true);
    }
}

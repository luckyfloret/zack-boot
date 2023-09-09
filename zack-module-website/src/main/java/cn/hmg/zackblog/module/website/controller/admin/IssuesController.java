package cn.hmg.zackblog.module.website.controller.admin;

import cn.hmg.zackblog.framework.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.operatelog.core.annotation.OperateLog;
import cn.hmg.zackblog.framework.operatelog.core.enums.OperateLogTypeEnum;
import cn.hmg.zackblog.module.website.controller.admin.vo.issues.IssuesPageReqVO;
import cn.hmg.zackblog.module.website.controller.admin.vo.issues.IssuesPageRespVO;
import cn.hmg.zackblog.module.website.controller.admin.vo.issues.IssuesRespVO;
import cn.hmg.zackblog.module.website.convert.admin.issues.IssuesConvert;
import cn.hmg.zackblog.module.website.entity.Issues;
import cn.hmg.zackblog.module.website.service.IIssuesService;
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
 * 问题管理表 前端控制器
 * </p>
 *
 * @author hmg
 * @since 2023-08-30
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "后台-issues管理")
@RequestMapping("/admin/website/issues")
public class IssuesController {
    private final IIssuesService issuesService;

    @GetMapping("/page")
    @PreAuthorize("@spe.hasPermission('website:issues:list')")
    @Operation(summary = "issues 分页列表")
    @OperateLog(operateName = "issues 分页列表", operateType = QUERY)
    public CommonResult<PageResult<IssuesPageRespVO>> page(@Valid IssuesPageReqVO reqVO) {
        return success(IssuesConvert.INSTANCE.convert(issuesService.getPage(reqVO)));
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("@spe.hasPermission('website:issues:query')")
    @Operation(summary = "根据id获取issues")
    @OperateLog(operateName = "根据id获取issues", operateType = QUERY)
    public CommonResult<IssuesRespVO> getIssuesById(@PathVariable("id") Long id){
        return success(IssuesConvert.INSTANCE.convert(issuesService.getIssuesById(id)));
    }

    @PutMapping("/update-status/{id}")
    @PreAuthorize("@spe.hasPermission('website:issues:update-status')")
    @Operation(summary = "issues 更新状态")
    @OperateLog(operateName = "issues 更新状态", operateType = UPDATE)
    public CommonResult<Boolean> updateStatus(@PathVariable("id") Long id) {
        issuesService.updateStatus(id);
        return success(true);
    }
}

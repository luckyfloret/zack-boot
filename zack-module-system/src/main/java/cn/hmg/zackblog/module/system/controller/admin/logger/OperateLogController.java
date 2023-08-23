package cn.hmg.zackblog.module.system.controller.admin.logger;

import cn.hmg.zackblog.framework.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.system.controller.admin.logger.vo.operatelog.OperateLogPageReqVO;
import cn.hmg.zackblog.module.system.controller.admin.logger.vo.operatelog.OperateLogPageRespVO;
import cn.hmg.zackblog.module.system.controller.admin.logger.vo.operatelog.OperateLogRespVO;
import cn.hmg.zackblog.module.system.convert.logger.OperateLogConvert;
import cn.hmg.zackblog.module.system.entity.logger.OperateLog;
import cn.hmg.zackblog.module.system.service.logger.IOperateLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static cn.hmg.zackblog.framework.common.pojo.CommonResult.success;

/**
 * <p>
 * 操作日志 前端控制器
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@RestController
@Tag(name = "后台-操作日志")
@RequestMapping("/admin/system/operate-log")
@RequiredArgsConstructor
public class OperateLogController {
    private final IOperateLogService operateLogService;

    @GetMapping("/page")
    @Operation(summary = "操作日志分页列表")
    @PreAuthorize("@spe.hasPermission('system:operate-log:list')")
    public CommonResult<PageResult<OperateLogPageRespVO>> page(@Valid OperateLogPageReqVO operateLogPageReqVO) {
        return success(OperateLogConvert.INSTANCE.convert(operateLogService.getPage(operateLogPageReqVO)));
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "根据id获取操作日志")
    @PreAuthorize("@spe.hasPermission('system:operate-log:query')")
    public CommonResult<OperateLogRespVO> getOperateLogById(@PathVariable("id") Long id) {
        return success(OperateLogConvert.INSTANCE.convert(operateLogService.getOperateLogById(id)));
    }
}

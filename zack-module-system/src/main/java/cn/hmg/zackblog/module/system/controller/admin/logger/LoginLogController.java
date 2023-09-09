package cn.hmg.zackblog.module.system.controller.admin.logger;

import cn.hmg.zackblog.framework.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.system.controller.admin.logger.vo.loginlog.LoginLogPageReqVO;
import cn.hmg.zackblog.module.system.controller.admin.logger.vo.loginlog.LoginLogPageRespVO;
import cn.hmg.zackblog.module.system.controller.admin.logger.vo.loginlog.LoginLogRespVO;
import cn.hmg.zackblog.module.system.convert.logger.LoginLogConvert;
import cn.hmg.zackblog.module.system.entity.logger.LoginLog;
import cn.hmg.zackblog.module.system.service.logger.ILoginLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static cn.hmg.zackblog.framework.common.pojo.CommonResult.success;

/**
 * <p>
 * 登录日志 前端控制器
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@RestController
@Tag(name = "后台-登录日志")
@RequestMapping("/admin/system/login-log")
@RequiredArgsConstructor
public class LoginLogController {
    private final ILoginLogService loginLogService;

    @GetMapping("/page")
    @PreAuthorize("@spe.hasPermission('system:login-log:list')")
    @Operation(summary = "登录日志分页列表")
    public CommonResult<PageResult<LoginLogPageRespVO>> page(@Valid LoginLogPageReqVO loginLogPageReqVO) {
        return success(LoginLogConvert.INSTANCE.convert(loginLogService.getPage(loginLogPageReqVO)));
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("@spe.hasPermission('system:login-log:query')")
    @Operation(summary = "根据id获取登录日志")
    public CommonResult<LoginLogRespVO> getLoginLogById(@PathVariable("id") Long id) {
        return success(LoginLogConvert.INSTANCE.convert(loginLogService.getLoginLogById(id)));
    }
}

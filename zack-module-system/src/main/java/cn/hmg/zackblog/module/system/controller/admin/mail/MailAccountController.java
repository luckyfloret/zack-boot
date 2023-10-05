package cn.hmg.zackblog.module.system.controller.admin.mail;

import cn.hmg.zackblog.framework.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.operatelog.core.annotation.OperateLog;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.account.*;
import cn.hmg.zackblog.module.system.convert.mail.MailAccountConvert;
import cn.hmg.zackblog.module.system.service.mail.IMailAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static cn.hmg.zackblog.framework.common.pojo.CommonResult.success;
import static cn.hmg.zackblog.framework.operatelog.core.enums.OperateLogTypeEnum.*;

/**
 * <p>
 * 邮件账号管理 前端控制器
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "后台-邮箱账号")
@RequestMapping("/admin/system/mail-account")
public class MailAccountController {
    private final IMailAccountService mailAccountService;

    @GetMapping("/page")
    @Operation(summary = "邮箱账号分页列表")
    @PreAuthorize("@spe.hasPermission('system:mail-account:list')")
    public CommonResult<PageResult<MailAccountPageRespVO>> page(@Valid MailAccountPageReqVO reqVO) {
        return success(MailAccountConvert.INSTANCE.convert(mailAccountService.getPage(reqVO)));
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "根据id获取邮箱账号")
    @PreAuthorize("@spe.hasPermission('system:mail-account:query')")
    public CommonResult<MailAccountRespVO> getMailAccountById(@PathVariable("id") Long id) {
        return success(MailAccountConvert.INSTANCE.convertMailAccountRespVO(mailAccountService.getMailAccountById(id)));
    }

    @PostMapping("/create")
    @Operation(summary = "创建邮箱账号")
    @OperateLog(operateName = "创建邮箱账号", operateType = CREATE)
    @PreAuthorize("@spe.hasPermission('system:mail-account:create')")
    public CommonResult<Boolean> createMailAccount(@Valid @RequestBody MailAccountCreateReqVO reqVO) {
        mailAccountService.createMailAccount(reqVO);
        return success(true);
    }

    @PutMapping("/update")
    @Operation(summary = "更新邮箱账号")
    @OperateLog(operateName = "更新邮箱账号", operateType = UPDATE)
    @PreAuthorize("@spe.hasPermission('system:mail-account:update')")
    public CommonResult<Boolean> updateMailAccount(@Valid @RequestBody MailAccountUpdateReqVO reqVO) {
        mailAccountService.updateMailAccount(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除邮箱账号")
    @OperateLog(operateName = "删除邮箱账号", operateType = DELETE)
    @PreAuthorize("@spe.hasPermission('system:mail-account:delete')")
    public CommonResult<Boolean> deleteMailAccountById(@PathVariable("id") Long id) {
        mailAccountService.deleteMailAccountById(id);
        return success();
    }

    @GetMapping("/list-simple")
    @Operation(summary = "简单的邮箱账号列表")
    public CommonResult<List<MailAccountListSimpleRespVO>> listSimple() {
        return success(MailAccountConvert.INSTANCE.convert(mailAccountService.list()));
    }
}

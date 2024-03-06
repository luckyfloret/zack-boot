package cn.hmg.zackblog.module.system.controller.admin.mail;

import cn.hmg.zackblog.framework.common.enums.UserTypeEnum;
import cn.hmg.zackblog.framework.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.operatelog.core.annotation.OperateLog;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.template.*;
import cn.hmg.zackblog.module.system.convert.mail.MailTemplateConvert;
import cn.hmg.zackblog.module.system.service.mail.IMailTemplateService;
import cn.hmg.zackblog.module.system.service.mail.MailSendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static cn.hmg.zackblog.framework.common.pojo.CommonResult.success;
import static cn.hmg.zackblog.framework.operatelog.core.enums.OperateLogTypeEnum.*;
import static cn.hmg.zackblog.framework.security.core.utils.SecurityUtils.getLoginUserId;

/**
 * <p>
 * 邮件模板 前端控制器
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@RestController
@Tag(name = "后台-邮件模板")
@RequestMapping("/admin/system/mail-template")
@RequiredArgsConstructor
public class MailTemplateController {
    private final IMailTemplateService mailTemplateService;

    private final MailSendService mailSendService;

    @GetMapping("/page")
    @Operation(summary = "邮件模板分页列表")
    @PreAuthorize("@spe.hasPermission('system:mail-template:list')")
    public CommonResult<PageResult<MailTemplatePageRespVO>> page(@Valid MailTemplatePageReqVO reqVO) {
        return success(MailTemplateConvert.INSTANCE.convert(mailTemplateService.getPage(reqVO)));
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "根据id获取邮件模板")
    @PreAuthorize("@spe.hasPermission('system:mail-template:query')")
    public CommonResult<MailTemplateRespVO> getMailTemplateById(@PathVariable("id") Long id) {
        return success(MailTemplateConvert.INSTANCE.convertMailTemplateRespVO(mailTemplateService.getMailTemplateById(id)));
    }

    @PostMapping("/create")
    @Operation(summary = "创建邮件模板")
    @OperateLog(operateName = "创建邮件模板", operateType = CREATE)
    @PreAuthorize("@spe.hasPermission('system:mail-template:create')")
    public CommonResult<Boolean> createMailTemplate(@Valid @RequestBody MailTemplateCreateReqVO reqVO) {
        mailTemplateService.createMailTemplate(reqVO);
        return success(true);
    }

    @PutMapping("/update")
    @Operation(summary = "更新邮件模板")
    @OperateLog(operateName = "更新邮件模板", operateType = UPDATE)
    @PreAuthorize("@spe.hasPermission('system:mail-template:update')")
    public CommonResult<Boolean> updateMailTemplate(@Valid @RequestBody MailTemplateUpdateReqVO reqVO) {
        mailTemplateService.updateMailTemplate(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除邮件模板")
    @OperateLog(operateName = "删除邮件模板", operateType = DELETE)
    @PreAuthorize("@spe.hasPermission('system:mail-template:delete')")
    public CommonResult<Boolean> deleteMailTemplateById(@PathVariable("id") Long id) {
        mailTemplateService.deleteMailTemplateById(id);
        return success(true);
    }

    @PostMapping("/test-send-mail")
    @Operation(summary = "测试邮件模板发送")
    @PreAuthorize("@spe.hasPermission('system:mail-template:test-send')")
        public CommonResult<Boolean> testSendMail(@Valid @RequestBody MailTemplateSendReqVO reqVO){
        mailSendService.send(reqVO.getToMail(), reqVO.getTemplateCode(), getLoginUserId(), UserTypeEnum.FRONT_USER.getType(), reqVO.getTemplateParams());
        return success(true);
    }
}

package cn.hmg.zackblog.module.system.controller.admin.mail;

import cn.hmg.zackblog.framework.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.record.MailRecordPageReqVO;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.record.MailRecordPageRespVO;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.record.MailRecordRespVO;
import cn.hmg.zackblog.module.system.convert.mail.MailRecordConvert;
import cn.hmg.zackblog.module.system.service.mail.IMailRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static cn.hmg.zackblog.framework.common.pojo.CommonResult.success;

/**
 * <p>
 * 邮件记录 前端控制器
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "后台-邮件记录")
@RequestMapping("/admin/system/mail-record")
public class MailRecordController {
    private final IMailRecordService mailRecordService;

    @GetMapping("/page")
    @Operation(summary = "邮件记录分页列表")
    @PreAuthorize("@spe.hasPermission('system:mail-record:list')")
    public CommonResult<PageResult<MailRecordPageRespVO>> page(@Valid MailRecordPageReqVO reqVO) {
        return success(MailRecordConvert.INSTANCE.convert(mailRecordService.getPage(reqVO)));
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "根据id获取邮件记录")
    @PreAuthorize("@spe.hasPermission('system:mail-record:query')")
    public CommonResult<MailRecordRespVO> getMailRecordById(@PathVariable("id") Long id) {
        return success(MailRecordConvert.INSTANCE.convert(mailRecordService.getMailRecordById(id)));
    }
}

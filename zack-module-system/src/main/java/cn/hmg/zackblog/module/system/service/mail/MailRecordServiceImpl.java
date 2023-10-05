package cn.hmg.zackblog.module.system.service.mail;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.system.controller.admin.mail.vo.record.MailRecordPageReqVO;
import cn.hmg.zackblog.module.system.entity.mail.MailAccount;
import cn.hmg.zackblog.module.system.entity.mail.MailRecord;
import cn.hmg.zackblog.module.system.entity.mail.MailTemplate;
import cn.hmg.zackblog.module.system.mapper.mail.MailRecordMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;

import static cn.hutool.core.exceptions.ExceptionUtil.getRootCauseMessage;

/**
 * <p>
 * 邮件记录 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Service
public class MailRecordServiceImpl extends ServiceImpl<MailRecordMapper, MailRecord> implements IMailRecordService {

    @Resource
    private MailRecordMapper mailRecordMapper;

    @Override
    public void createMailRecord(Long userId, Integer userType, String toMail, MailAccount mailAccount,
                                 MailTemplate mailTemplate, String templateContent, Map<String, Object> templateParams,
                                 Integer sendStatus, LocalDateTime sendTime, Exception exception) {
        MailRecord mailRecord = MailRecord.builder()
                .userId(userId)
                .userType(userType)
                .toMail(toMail)
                .fromMail(mailAccount.getEmail())
                .accountId(mailAccount.getId())
                .templateId(mailTemplate.getId())
                .templateCode(mailTemplate.getCode())
                .templateTitle(mailTemplate.getTitle())
                .templateParams(templateParams)
                .templateContent(templateContent)
                .senderNickname(mailTemplate.getNickname())
                .sendStatus(sendStatus)
                .sendTime(sendTime)
                .sendException(exception == null ? "" : getRootCauseMessage(exception))
                .build();

        //记录邮件发送
        mailRecordMapper.insert(mailRecord);
    }

    @Override
    public PageResult<MailRecord> getPage(MailRecordPageReqVO reqVO) {
        return mailRecordMapper.getPage(reqVO);
    }

    @Override
    public MailRecord getMailRecordById(Long id) {
        return mailRecordMapper.selectById(id);
    }
}

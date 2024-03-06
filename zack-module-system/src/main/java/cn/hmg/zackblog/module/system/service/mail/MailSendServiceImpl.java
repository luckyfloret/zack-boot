package cn.hmg.zackblog.module.system.service.mail;


import cn.hmg.zackblog.framework.common.exception.BusinessException;
import cn.hmg.zackblog.framework.common.exception.ServerException;
import cn.hmg.zackblog.framework.mail.core.MailClient;
import cn.hmg.zackblog.framework.mail.core.factory.MailClientFactory;
import cn.hmg.zackblog.module.system.entity.mail.MailAccount;
import cn.hmg.zackblog.module.system.entity.mail.MailTemplate;
import cn.hmg.zackblog.module.system.mq.producer.mail.MailSendMessage;
import cn.hmg.zackblog.module.system.mq.producer.mail.MailSendProducer;
import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import static cn.hmg.zackblog.framework.common.enums.CommonStatusEnum.DISABLED;
import static cn.hmg.zackblog.framework.common.enums.MailSendStatusEnum.SEND_FAIL;
import static cn.hmg.zackblog.framework.common.enums.MailSendStatusEnum.SEND_SUCCESS;
import static cn.hmg.zackblog.module.system.enums.ErrorCodeEnum.*;
import static cn.hutool.core.exceptions.ExceptionUtil.getRootCauseMessage;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-30 16:20
 * @description: 邮箱发送服务实现类
 */
@Service
@Slf4j
public class MailSendServiceImpl implements MailSendService {

    @Resource
    private MailClientFactory mailClientFactory;

    @Resource
    private IMailTemplateService mailTemplateService;

    @Resource
    private IMailAccountService mailAccountService;

    @Resource
    private IMailRecordService mailRecordService;

    @Resource
    private MailSendProducer mailSendProducer;

    @Override
    public void send(String toMail, String templateCode, Long userId, Integer userType, Map<String, Object> params) {
        try {
            //校验邮件模板是否合法
            MailTemplate mailTemplate = verifyMailTemplate(templateCode);

            //校验邮箱账号
            MailAccount mailAccount = verifyMailAccount(mailTemplate.getAccountId());

            //校验参数是否合法并解析参数
            verifyTemplateParams(mailTemplate, params);

            //执行发送
            String content = mailTemplateService.formatTemplateParams(mailTemplate.getContent(), params);
            mailSendProducer.asyncSendMailMessage(toMail, templateCode, content, userId, userType, mailAccount, mailTemplate, params);
        } catch (Exception e) {
            //记录发送失败日志
            log.error("邮件校验异常 => {}", getRootCauseMessage(e));
        }

    }

    @Override
    public void doSendMail(MailSendMessage message) {
        try {
            MailClient mailClient = mailClientFactory.getMailClient(message.getTemplateCode());
            mailClient.send(message.getToMail(), message.getContent(), true);
            //记录发送成功日志
            mailRecordService.createMailRecord(message.getUserId(), message.getUserType(), message.getToMail(),
                    message.getMailAccount(), message.getMailTemplate(), message.getContent(), message.getParams(),
                    SEND_SUCCESS.getStatus(), LocalDateTime.now(), null);
        } catch (Exception e) {
            log.error("邮件发送异常...");
            //记录发送失败日志
            mailRecordService.createMailRecord(message.getUserId(), message.getUserType(), message.getToMail(),
                    message.getMailAccount(), message.getMailTemplate(), message.getContent(), message.getParams(),
                    SEND_FAIL.getStatus(), LocalDateTime.now(), e);
        }
    }

    private MailAccount verifyMailAccount(Long accountId) {
        MailAccount mailAccount = mailAccountService.selectOneByIdFromCache(accountId);

        if (Objects.isNull(mailAccount)) {
            throw new BusinessException(MAIL_ACCOUNT_NOT_EXISTS.getCode(), MAIL_ACCOUNT_NOT_EXISTS.getMessage());
        }

        return mailAccount;
    }

    private void verifyTemplateParams(MailTemplate mailTemplate, Map<String, Object> params) {
        mailTemplate.getParams().forEach(key -> {
            Object param = params.get(key);
            if (Objects.isNull(param)) {
                throw new BusinessException(MAIL_SEND_TEMPLATE_PARAM_NOT_EXISTS.getCode(),
                        MAIL_SEND_TEMPLATE_PARAM_NOT_EXISTS.getMessage());
            }
        });
    }

    private MailTemplate verifyMailTemplate(String templateCode) {
        MailTemplate mailTemplate = mailTemplateService.getMailTemplateByCodeFromCache(templateCode);

        if (Objects.isNull(mailTemplate)) {
            throw new BusinessException(MAIL_TEMPLATE_NOT_EXISTS.getCode(), MAIL_TEMPLATE_NOT_EXISTS.getMessage());
        }

        if (DISABLED.getStatusCode().equals(mailTemplate.getStatus())) {
            throw new ServerException();
        }

        return mailTemplate;
    }
}

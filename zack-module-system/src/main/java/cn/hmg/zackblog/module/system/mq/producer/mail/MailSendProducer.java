package cn.hmg.zackblog.module.system.mq.producer.mail;

import cn.hmg.zackblog.framework.rocketmq.core.RocketMQTemplateExt;
import cn.hmg.zackblog.module.system.entity.mail.MailAccount;
import cn.hmg.zackblog.module.system.entity.mail.MailTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.Map;
import java.util.UUID;

import static cn.hmg.zackblog.module.system.mq.topic.mail.MailSendTopic.MAIL_SEND;
import static cn.hmg.zackblog.module.system.mq.topic.mail.MailSendTopic.TAG;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-10-03 14:42
 * @description: 邮箱发送生产者
 */
@Component
public class MailSendProducer {
    @Resource
    private RocketMQTemplateExt rocketMQTemplateExt;

    public void asyncSendMailMessage(String toMail, String templateCode, String content, Long userId, Integer userType,
                                     MailAccount mailAccount, MailTemplate mailTemplate, Map<String, Object> params) {
        String topic = rocketMQTemplateExt.buildDestination(MAIL_SEND, TAG);

        MailSendMessage mailSendMessage = (MailSendMessage) new MailSendMessage()
                .setUserId(userId)
                .setUserType(userType)
                .setMailAccount(mailAccount)
                .setMailTemplate(mailTemplate)
                .setParams(params)
                .setToMail(toMail)
                .setTemplateCode(templateCode)
                .setContent(content)
                .setMessage("mail send...")
                .setSource("asyncSendMailMessage")
                .setKey(UUID.randomUUID().toString());
        rocketMQTemplateExt.asyncSend(topic, mailSendMessage);
    }

}

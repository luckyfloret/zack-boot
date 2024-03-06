package cn.hmg.zackblog.module.system.mq.producer.mail;

import cn.hmg.zackblog.framework.rocketmq.core.RocketMQTemplateExt;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

import static cn.hmg.zackblog.module.system.mq.topic.mail.MailAccountTopic.MAIL_ACCOUNT_REFRESH_CACHE;
import static cn.hmg.zackblog.module.system.mq.topic.mail.MailAccountTopic.TAG;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-10-03 14:42
 * @description: 邮件模板生产者，用于刷新本地缓存
 */
@Component
public class MailTemplateProducer {
    @Resource
    private RocketMQTemplateExt rocketMQTemplateExt;

    public void syncSendMailTemplateRefreshCacheMessage() {
        String topic = rocketMQTemplateExt.buildDestination(MAIL_ACCOUNT_REFRESH_CACHE, TAG);
        MailTemplateRefreshCacheMessage mailTemplateRefreshCacheMessage = new MailTemplateRefreshCacheMessage();
        mailTemplateRefreshCacheMessage.setKey(UUID.randomUUID().toString());
        mailTemplateRefreshCacheMessage.setMessage("mail account template refresh cache...");
        mailTemplateRefreshCacheMessage.setSource("asyncSendRefreshCacheMessage");
        rocketMQTemplateExt.send(topic, mailTemplateRefreshCacheMessage);
    }
}

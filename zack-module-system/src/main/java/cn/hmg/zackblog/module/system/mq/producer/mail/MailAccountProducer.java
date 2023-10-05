package cn.hmg.zackblog.module.system.mq.producer.mail;

import cn.hmg.zackblog.framework.rocketmq.core.RocketMQTemplateExt;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.UUID;

import static cn.hmg.zackblog.module.system.mq.topic.mail.MailAccountTopic.*;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-10-03 14:42
 * @description: 邮箱账号生产者，用于刷新本地缓存
 */
@Component
public class MailAccountProducer {
    @Resource
    private RocketMQTemplateExt rocketMQTemplateExt;

    public void syncSendMailAccountRefreshCacheMessage() {
        String topic = rocketMQTemplateExt.buildDestination(MAIL_ACCOUNT_REFRESH_CACHE, TAG);
        MailAccountRefreshCacheMessage mailAccountRefreshCacheMessage = new MailAccountRefreshCacheMessage();
        mailAccountRefreshCacheMessage.setKey(UUID.randomUUID().toString());
        mailAccountRefreshCacheMessage.setMessage("mail account template refresh cache...");
        mailAccountRefreshCacheMessage.setSource("asyncSendRefreshCacheMessage");
        rocketMQTemplateExt.send(topic, mailAccountRefreshCacheMessage);
    }
}

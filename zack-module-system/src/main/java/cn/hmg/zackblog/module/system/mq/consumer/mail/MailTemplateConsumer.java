package cn.hmg.zackblog.module.system.mq.consumer.mail;

import cn.hmg.zackblog.framework.rocketmq.core.listener.AbstractRocketMQListener;
import cn.hmg.zackblog.module.system.mq.producer.mail.MailTemplateRefreshCacheMessage;
import cn.hmg.zackblog.module.system.service.mail.IMailTemplateService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static cn.hmg.zackblog.module.system.mq.topic.mail.MailTemplateTopic.*;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-10-03 15:21
 * @description: 邮箱账号与邮件模板消费者
 */
@Component
@RocketMQMessageListener(topic = MAIL_TEMPLATE_REFRESH_CACHE, consumerGroup = CONSUMER_GROUP)
public class MailTemplateConsumer extends AbstractRocketMQListener<MailTemplateRefreshCacheMessage>
        implements RocketMQListener<MailTemplateRefreshCacheMessage> {

    @Resource
    private IMailTemplateService mailTemplateService;

    @Override
    protected String consumerName() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected void handleMessage(MailTemplateRefreshCacheMessage message) throws Exception {
        mailTemplateService.initLocalCache();
    }

    @Override
    protected void overMaxRetryTimesMessage(MailTemplateRefreshCacheMessage message) {

    }

    @Override
    protected boolean isRetry() {
        return false;
    }

    @Override
    protected boolean isThrowException() {
        return false;
    }

    @Override
    public void onMessage(MailTemplateRefreshCacheMessage message) {
        super.dispatch(message);
    }
}

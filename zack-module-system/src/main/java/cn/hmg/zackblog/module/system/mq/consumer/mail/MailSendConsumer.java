package cn.hmg.zackblog.module.system.mq.consumer.mail;

import cn.hmg.zackblog.framework.rocketmq.core.listener.AbstractRocketMQListener;
import cn.hmg.zackblog.module.system.mq.producer.mail.MailSendMessage;
import cn.hmg.zackblog.module.system.service.mail.MailSendService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static cn.hmg.zackblog.module.system.mq.topic.mail.MailSendTopic.CONSUMER_GROUP;
import static cn.hmg.zackblog.module.system.mq.topic.mail.MailSendTopic.MAIL_SEND;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-10-03 15:01
 * @description: 邮箱发送消费者
 */
@Component
@RocketMQMessageListener(topic = MAIL_SEND, consumerGroup = CONSUMER_GROUP)
public class MailSendConsumer extends AbstractRocketMQListener<MailSendMessage> implements RocketMQListener<MailSendMessage> {
    @Resource
    private MailSendService mailSendService;

    @Override
    protected String consumerName() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected void handleMessage(MailSendMessage message) throws Exception {
        mailSendService.doSendMail(message);
    }

    @Override
    protected void overMaxRetryTimesMessage(MailSendMessage message) {

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
    public void onMessage(MailSendMessage message) {
        super.dispatch(message);
    }
}

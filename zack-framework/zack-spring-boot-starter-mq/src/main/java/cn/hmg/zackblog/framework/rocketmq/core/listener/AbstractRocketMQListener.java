package cn.hmg.zackblog.framework.rocketmq.core.listener;

import cn.hmg.zackblog.framework.common.utils.json.JsonUtils;
import cn.hmg.zackblog.framework.rocketmq.core.RocketMQTemplateExt;
import cn.hmg.zackblog.framework.rocketmq.core.message.AbstractMessageTemplate;
import cn.hutool.core.lang.Assert;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-27 23:31
 * @description: 抽象RocketMQ监听器， 用于自定义 消息过滤、重试、异常消息处理、日志记录等
 */
public abstract class AbstractRocketMQListener<T extends AbstractMessageTemplate> {
    /**
     * 这里的日志记录器是哪个子类的就会被哪个子类的类进行初始化
     */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final Integer DEFAULT_MAX_RETRY_TIMES = 12;

    private static final Integer DEFAULT_RETRY_DELAY_LEVEL = -1;

    @Resource
    private RocketMQTemplateExt rocketMQTemplateExt;

    /**
     * 消息者名称
     *
     * @return 消费者名称
     */
    protected abstract String consumerName();

    /**
     * 消息处理
     *
     * @param message 待处理消息
     * @throws Exception 消费异常
     */
    protected abstract void handleMessage(T message) throws Exception;

    /**
     * 超过重试次数消息，需要启用isRetry
     *
     * @param message 待处理消息
     */
    protected abstract void overMaxRetryTimesMessage(T message);

    /**
     * 是否过滤消息，例如某些
     *
     * @param message 待处理消息
     * @return true: 本次消息被过滤，false：不过滤
     */
    protected boolean isFilter(T message) {
        return false;
    }

    /**
     * 是否异常时重复发送
     *
     * @return true: 消息重试，false：不重试
     */
    protected abstract boolean isRetry();

    /**
     * 消费异常时是否抛出异常
     *
     * @return true: 抛出异常，false：消费异常(如果没有开启重试则消息会被自动ack)
     */
    protected abstract boolean isThrowException();

    /**
     * 最大重试次数
     *
     * @return 最大重试次数，默认12次
     */
    protected int maxRetryTimes() {
        return DEFAULT_MAX_RETRY_TIMES;
    }

    /**
     * isRetry开启时，重新入队延迟时间
     *
     * @return -1：立即入队重试
     */
    protected int retryDelayLevel() {
        return DEFAULT_RETRY_DELAY_LEVEL;
    }

    /**
     * 由父类来完成基础的日志和调配，下面的只是提供一个思路
     */
    public void dispatch(T message) {
        // 基础日志记录被父类处理了
        logger.info("[{}]消费者收到消息[{}]", consumerName(), JsonUtils.toJsonStr(message));
        if (isFilter(message)) {
            logger.warn("{} => 消息不满足消费条件，已过滤", message.getKey());
            return;
        }
        // 超过最大重试次数时调用子类方法处理
        if (message.getRetryTimes() > maxRetryTimes()) {
            overMaxRetryTimesMessage(message);
            return;
        }

        try {
            long start = System.currentTimeMillis();
            handleMessage(message);
            long end = System.currentTimeMillis();
            logger.info("[{} => 消息消费成功]，耗时[{}ms]", message.getKey(),  (end - start));
        } catch (Exception e) {
            logger.error("消息消费异常", e);
            // 是捕获异常还是抛出，由子类决定
            Assert.isTrue(!isThrowException(), () -> new RuntimeException(e));
            //判断是否需要重试
            if (isRetry()) {
                //处理重试
                handleRetry(message);
            }
        }
    }

    private void handleRetry(T message) {
        // 获取子类RocketMQMessageListener注解拿到topic和tag
        RocketMQMessageListener annotation = this.getClass().getAnnotation(RocketMQMessageListener.class);
        if (Objects.nonNull(annotation)) {
            message.setSource(message.getSource() + "===> 消息重试");
            message.setRetryTimes(message.getRetryTimes() + 1);
            SendResult sendResult;
            try {
                // 如果消息发送不成功，则再次重新发送，如果发送异常则抛出由MQ再次处理(异常时不走延迟消息)
                // 此处捕获之后，相当于此条消息被消息完成然后重新发送新的消息
                sendResult = rocketMQTemplateExt.send(annotation.topic(), annotation.selectorExpression(), message, retryDelayLevel());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            // 发送失败的处理就是不进行ACK，由RocketMQ重试
            Assert.isTrue(sendResult.getSendStatus() == SendStatus.SEND_OK, () -> new RuntimeException("重试消息发送失败"));
        }
    }
}

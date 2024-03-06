package cn.hmg.zackblog.framework.rocketmq.core;

import cn.hmg.zackblog.framework.common.utils.json.JsonUtils;
import cn.hmg.zackblog.framework.rocketmq.core.constants.RocketMQConstant;
import cn.hmg.zackblog.framework.rocketmq.core.message.AbstractMessageTemplate;
import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-27 15:13
 * @description: rocketmqTemplate增强类
 */
@RequiredArgsConstructor
public class RocketMQTemplateExt {
    private static final Logger LOGGER = LoggerFactory.getLogger(RocketMQTemplateExt.class);

    private final RocketMQTemplate rocketMQTemplate;

    /**
     * 获取模板，如果封装的方法不够提供原生的使用方式
     */
    public RocketMQTemplate getTemplate() {
        return rocketMQTemplate;
    }

    /**
     * 构建目的地
     */
    public String buildDestination(String topic, String tag) {
        return topic + RocketMQConstant.DELIMITER + tag;
    }

    /**
     * 发送同步消息
     */
    public <T extends AbstractMessageTemplate> SendResult send(String topic, String tag, T message) {
        // 注意分隔符
        return send(topic + RocketMQConstant.DELIMITER + tag, message);
    }

    public <T extends AbstractMessageTemplate> SendResult send(String destination, T message) {
        // 设置业务键，此处根据公共的参数进行处理
        // 更多的其它基础业务处理...
        Message<T> sendMessage = MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, message.getKey()).build();
        SendResult sendResult = rocketMQTemplate.syncSend(destination, sendMessage);
        // 此处为了方便查看给日志转了json，根据选择选择日志记录方式，例如ELK采集
        LOGGER.info("[{}]同步消息[{}]发送结果[{}]", destination, JsonUtils.toJsonStr(message), JSONObject.toJSON(sendResult));
        return sendResult;
    }

    /**
     * 发送异步消息
     */
    public <T extends AbstractMessageTemplate> void asyncSend(String destination, T message){
        Message<T> sendMessage = MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, message.getKey()).build();
        SendCallback sendCallback = new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                LOGGER.info("success callback，messageId => {}", sendResult);
            }

            @Override
            public void onException(Throwable e) {
                LOGGER.error("fail callback，Exception Message => {}", e.getMessage());
            }
        };
        rocketMQTemplate.asyncSend(destination, sendMessage, sendCallback);
    }

    public <T extends AbstractMessageTemplate> void asyncSend(String destination, T message, SendCallback sendCallback){
        //构建sendMessage
        Message<T> sendMessage = MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, message.getKey()).build();
        //发送异步消息
        rocketMQTemplate.asyncSend(destination, sendMessage, sendCallback);
    }

    /**
     * 发送延迟消息
     */
    public <T extends AbstractMessageTemplate> SendResult send(String topic, String tag, T message, int delayLevel) {
        return send(topic + RocketMQConstant.DELIMITER + tag, message, delayLevel);
    }

    public <T extends AbstractMessageTemplate> SendResult send(String destination, T message, int delayLevel) {
        Message<T> sendMessage = MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, message.getKey()).build();
        SendResult sendResult = rocketMQTemplate.syncSend(destination, sendMessage, 3000, delayLevel);
        LOGGER.info("[{}]延迟等级[{}]消息[{}]发送结果[{}]", destination, delayLevel, JsonUtils.toJsonStr(message), JsonUtils.toJsonStr(sendResult));
        return sendResult;
    }
}

package cn.hmg.zackblog.framework.rocketmq.core.message;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-27 11:31
 * @description: rocketmq 消息模板
 */
@Data
public abstract class AbstractMessageTemplate {
    /**
     * 业务键，用于RocketMQ控制台查看消费情况
     */
    private String key;

    /**
     * 发送消息来源，用于排查问题
     */
    private String source = "";


    /**
     * 发送时间
     */
    private LocalDateTime sendTime = LocalDateTime.now();

    /**
     * 跟踪id，用于slf4j等日志记录跟踪id，方便查询业务链
     */
    private String traceId = UUID.randomUUID().toString();

    /**
     * 重试次数，用于判断重试次数，超过重试次数发送异常警告
     */
    private Integer retryTimes = 0;
}

package cn.hmg.zackblog.module.system.mq.consumer.permission;

import cn.hmg.zackblog.framework.rocketmq.core.listener.AbstractRocketMQListener;
import cn.hmg.zackblog.module.system.mq.producer.permission.PermissionRefreshCacheMessage;
import cn.hmg.zackblog.module.system.service.permission.PermissionService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static cn.hmg.zackblog.module.system.mq.topic.permission.PermissionTopic.*;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-28 17:33
 * @description: permission刷新缓存消费者
 */
@Component
@RocketMQMessageListener(topic = PERMISSION_REFRESH_CACHE, consumerGroup = CONSUMER_GROUP)
public class PermissionRefreshCacheConsumer extends AbstractRocketMQListener<PermissionRefreshCacheMessage> implements RocketMQListener<PermissionRefreshCacheMessage> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private PermissionService permissionService;

    @Override
    protected String consumerName() {
        return "PermissionRefreshCacheConsumer";
    }

    @Override
    protected void handleMessage(PermissionRefreshCacheMessage message) throws Exception {
        permissionService.initLocalCache();
    }

    @Override
    protected void overMaxRetryTimesMessage(PermissionRefreshCacheMessage message) {

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
    public void onMessage(PermissionRefreshCacheMessage message) {
        super.dispatch(message);
    }
}

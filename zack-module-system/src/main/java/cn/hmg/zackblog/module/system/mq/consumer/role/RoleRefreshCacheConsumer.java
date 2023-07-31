package cn.hmg.zackblog.module.system.mq.consumer.role;

import cn.hmg.zackblog.framework.rocketmq.core.listener.AbstractRocketMQListener;
import cn.hmg.zackblog.module.system.mq.producer.role.RoleRefreshCacheMessage;
import cn.hmg.zackblog.module.system.service.permission.IRoleService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static cn.hmg.zackblog.module.system.mq.topic.role.RoleTopic.*;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-28 17:33
 * @description: 角色刷新缓存消费者
 */
@Component
@RocketMQMessageListener(topic = ROLE_REFRESH_CACHE, consumerGroup = CONSUMER_GROUP)
public class RoleRefreshCacheConsumer extends AbstractRocketMQListener<RoleRefreshCacheMessage> implements RocketMQListener<RoleRefreshCacheMessage> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private IRoleService roleService;

    @Override
    protected String consumerName() {
        return "RoleRefreshCacheConsumer";
    }

    @Override
    protected void handleMessage(RoleRefreshCacheMessage message) throws Exception {
        roleService.initLocalCache();
    }

    @Override
    protected void overMaxRetryTimesMessage(RoleRefreshCacheMessage message) {

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
    public void onMessage(RoleRefreshCacheMessage message) {
        super.dispatch(message);
    }
}

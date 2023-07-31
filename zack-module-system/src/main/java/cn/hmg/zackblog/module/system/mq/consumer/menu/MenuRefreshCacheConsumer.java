package cn.hmg.zackblog.module.system.mq.consumer.menu;

import cn.hmg.zackblog.framework.rocketmq.core.listener.AbstractRocketMQListener;
import cn.hmg.zackblog.module.system.mq.producer.menu.MenuRefreshCacheMessage;
import cn.hmg.zackblog.module.system.service.permission.IMenuService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static cn.hmg.zackblog.module.system.mq.topic.menu.MenuTopic.*;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-28 17:33
 * @description: 菜单刷新缓存消费者
 */
@Component
@RocketMQMessageListener(topic = MENU_REFRESH_CACHE, consumerGroup = CONSUMER_GROUP)
public class MenuRefreshCacheConsumer extends AbstractRocketMQListener<MenuRefreshCacheMessage> implements RocketMQListener<MenuRefreshCacheMessage> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private IMenuService menuService;

    @Override
    protected String consumerName() {
        return "MenuRefreshCacheConsumer";
    }

    @Override
    protected void handleMessage(MenuRefreshCacheMessage message) throws Exception {
        menuService.initLocalCache();
    }

    @Override
    protected void overMaxRetryTimesMessage(MenuRefreshCacheMessage message) {

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
    public void onMessage(MenuRefreshCacheMessage message) {
        super.dispatch(message);
    }
}

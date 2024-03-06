package cn.hmg.zackblog.module.system.mq.producer.menu;

import cn.hmg.zackblog.framework.rocketmq.core.RocketMQTemplateExt;
import cn.hmg.zackblog.module.system.mq.topic.menu.MenuTopic;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-28 17:14
 * @description: menu生产者
 */
@Component
public class MenuProducer {
    @Resource
    private RocketMQTemplateExt rocketMQTemplateExt;

    public void syncSendMenuRefreshCacheMessage() {
        String topic = rocketMQTemplateExt.buildDestination(MenuTopic.MENU_REFRESH_CACHE, MenuTopic.TAG);
        MenuRefreshCacheMessage menuRefreshCacheMessage = new MenuRefreshCacheMessage();
        menuRefreshCacheMessage.setMessage("refresh menu cache...");
        menuRefreshCacheMessage.setKey(UUID.randomUUID().toString());
        menuRefreshCacheMessage.setSource("syncSendMenuRefreshCacheMessage");
        rocketMQTemplateExt.send(topic, menuRefreshCacheMessage);
    }
}

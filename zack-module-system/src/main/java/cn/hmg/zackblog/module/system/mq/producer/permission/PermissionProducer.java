package cn.hmg.zackblog.module.system.mq.producer.permission;

import cn.hmg.zackblog.framework.rocketmq.core.RocketMQTemplateExt;
import cn.hmg.zackblog.module.system.mq.topic.permission.PermissionTopic;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-28 17:14
 * @description: permission生产者
 */
@Component
public class PermissionProducer {
    @Resource
    private RocketMQTemplateExt rocketMQTemplateExt;

    public void asyncSendPermissionRefreshCacheMessage() {
        String topic = rocketMQTemplateExt.buildDestination(PermissionTopic.PERMISSION_REFRESH_CACHE, PermissionTopic.TAG);
        PermissionRefreshCacheMessage menuRefreshCacheMessage = new PermissionRefreshCacheMessage();
        menuRefreshCacheMessage.setMessage("refresh permission cache...");
        menuRefreshCacheMessage.setKey(UUID.randomUUID().toString());
        menuRefreshCacheMessage.setSource("asyncSendPermissionRefreshCacheMessage");
        rocketMQTemplateExt.asyncSend(topic, menuRefreshCacheMessage);
    }
}

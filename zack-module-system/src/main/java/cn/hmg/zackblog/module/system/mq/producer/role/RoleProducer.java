package cn.hmg.zackblog.module.system.mq.producer.role;

import cn.hmg.zackblog.framework.rocketmq.core.RocketMQTemplateExt;
import cn.hmg.zackblog.module.system.mq.topic.role.RoleTopic;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-28 17:14
 * @description: 角色生产者
 */
@Component
public class RoleProducer {
    @Resource
    private RocketMQTemplateExt rocketMQTemplateExt;

    public void syncSendRoleRefreshCacheMessage() {
        String topic = rocketMQTemplateExt.buildDestination(RoleTopic.ROLE_REFRESH_CACHE, RoleTopic.TAG);
        RoleRefreshCacheMessage menuRefreshCacheMessage = new RoleRefreshCacheMessage();
        menuRefreshCacheMessage.setMessage("refresh role cache...");
        menuRefreshCacheMessage.setKey(UUID.randomUUID().toString());
        menuRefreshCacheMessage.setSource("syncSendRoleRefreshCacheMessage");
        rocketMQTemplateExt.send(topic, menuRefreshCacheMessage);
    }
}

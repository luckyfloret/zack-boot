package cn.hmg.zackblog.module.infra.mq.producer.file.config;

import cn.hmg.zackblog.framework.rocketmq.core.RocketMQTemplateExt;
import cn.hmg.zackblog.module.infra.mq.topic.file.config.FileConfigTopic;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-28 23:23
 * @description:
 */
@Component
public class FileConfigProducer {
    @Resource
    private RocketMQTemplateExt rocketMQTemplateExt;

    public void syncSendFileConfigRefreshCacheMessage() {
        String topic = rocketMQTemplateExt.buildDestination(FileConfigTopic.FILE_CONFIG_REFRESH_CACHE, FileConfigTopic.TAG);
        FileConfigRefreshCacheMessage fileConfigRefreshCacheMessage = new FileConfigRefreshCacheMessage();
        fileConfigRefreshCacheMessage.setMessage("refresh file config cache...");
        fileConfigRefreshCacheMessage.setKey(UUID.randomUUID().toString());
        fileConfigRefreshCacheMessage.setSource("syncSendFileConfigRefreshCacheMessage");
        rocketMQTemplateExt.send(topic, fileConfigRefreshCacheMessage);
    }
}

package cn.hmg.zackblog.module.article.mq.producer.tags;

import cn.hmg.zackblog.framework.rocketmq.core.RocketMQTemplateExt;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.UUID;

import static cn.hmg.zackblog.module.article.mq.topic.tags.TagsTopic.TAG;
import static cn.hmg.zackblog.module.article.mq.topic.tags.TagsTopic.TAGS_REFRESH_CACHE;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-17 11:26
 * @description: 标签生产者
 */
@Component
public class TagsProducer {
    @Resource
    private RocketMQTemplateExt rocketMQTemplateExt;

    public void syncSendTagsRefreshCacheMessage() {
        String topic = rocketMQTemplateExt.buildDestination(TAGS_REFRESH_CACHE, TAG);
        TagsRefreshCacheMessage tagsRefreshCacheMessage = new TagsRefreshCacheMessage();
        tagsRefreshCacheMessage.setMessage("refresh tags cache...");
        tagsRefreshCacheMessage.setKey(UUID.randomUUID().toString());
        tagsRefreshCacheMessage.setSource("syncSendTagsRefreshCacheMessage");
        tagsRefreshCacheMessage.setSendTime(LocalDateTime.now());
        rocketMQTemplateExt.send(topic, tagsRefreshCacheMessage);
    }
}

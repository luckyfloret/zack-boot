package cn.hmg.zackblog.module.article.mq.producer.category;

import cn.hmg.zackblog.framework.rocketmq.core.RocketMQTemplateExt;
import cn.hmg.zackblog.module.article.mq.topic.category.CategoryTopic;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.UUID;

import static cn.hmg.zackblog.module.article.mq.topic.category.CategoryTopic.CATEGORY_REFRESH_CACHE;
import static cn.hmg.zackblog.module.article.mq.topic.tags.TagsTopic.TAG;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-17 11:25
 * @description: 分类生产者
 */
@Component
public class CategoryProducer {
    @Resource
    private RocketMQTemplateExt rocketMQTemplateExt;

    public void asyncSendCategoryRefreshCacheMessage() {
        String topic = rocketMQTemplateExt.buildDestination(CATEGORY_REFRESH_CACHE, TAG);
        CategoryRefreshCacheMessage categoryRefreshCacheMessage = new CategoryRefreshCacheMessage();
        categoryRefreshCacheMessage.setMessage("refresh category cache...");
        categoryRefreshCacheMessage.setKey(UUID.randomUUID().toString());
        categoryRefreshCacheMessage.setSource("asyncSendCategoryRefreshCacheMessage");
        categoryRefreshCacheMessage.setSendTime(LocalDateTime.now());
        rocketMQTemplateExt.asyncSend(topic, categoryRefreshCacheMessage);

    }
}

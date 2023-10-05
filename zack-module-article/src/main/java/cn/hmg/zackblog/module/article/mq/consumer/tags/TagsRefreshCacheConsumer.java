package cn.hmg.zackblog.module.article.mq.consumer.tags;

import cn.hmg.zackblog.framework.rocketmq.core.listener.AbstractRocketMQListener;
import cn.hmg.zackblog.module.article.mq.producer.tags.TagsRefreshCacheMessage;
import cn.hmg.zackblog.module.article.service.tags.ITagsService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static cn.hmg.zackblog.module.article.mq.topic.tags.TagsTopic.CONSUMER_GROUP;
import static cn.hmg.zackblog.module.article.mq.topic.tags.TagsTopic.TAGS_REFRESH_CACHE;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-17 14:41
 * @description: 标签刷新缓存消费者
 */
@Component
@RocketMQMessageListener(topic = TAGS_REFRESH_CACHE, consumerGroup = CONSUMER_GROUP)
public class TagsRefreshCacheConsumer extends AbstractRocketMQListener<TagsRefreshCacheMessage> implements RocketMQListener<TagsRefreshCacheMessage> {

    @Resource
    private ITagsService tagsService;

    @Override
    protected String consumerName() {
        return "TagsRefreshCacheConsumer";
    }

    @Override
    protected void handleMessage(TagsRefreshCacheMessage message) throws Exception {
        tagsService.initTagsLocalCache();
    }

    @Override
    protected void overMaxRetryTimesMessage(TagsRefreshCacheMessage message) {

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
    public void onMessage(TagsRefreshCacheMessage message) {
        super.dispatch(message);
    }
}

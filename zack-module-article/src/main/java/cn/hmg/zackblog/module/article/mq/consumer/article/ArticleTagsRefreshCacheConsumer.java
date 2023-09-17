package cn.hmg.zackblog.module.article.mq.consumer.article;

import cn.hmg.zackblog.framework.rocketmq.core.listener.AbstractRocketMQListener;
import cn.hmg.zackblog.module.article.mq.producer.article.ArticleTagsRefreshCacheMessage;
import cn.hmg.zackblog.module.article.service.article.IArticleTagsService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static cn.hmg.zackblog.module.article.mq.topic.article.ArticleTagsTopic.ARTICLE_TAGS_REFRESH_CACHE;
import static cn.hmg.zackblog.module.article.mq.topic.tags.TagsTopic.CONSUMER_GROUP;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-17 14:41
 * @description: 文章标签刷新缓存消费者
 */
@Component
@RocketMQMessageListener(topic = ARTICLE_TAGS_REFRESH_CACHE,consumerGroup = CONSUMER_GROUP)
public class ArticleTagsRefreshCacheConsumer extends AbstractRocketMQListener<ArticleTagsRefreshCacheMessage> implements RocketMQListener<ArticleTagsRefreshCacheMessage> {

    @Resource
    private IArticleTagsService articleTagsService;

    @Override
    protected String consumerName() {
        return "ArticleTagsRefreshCacheConsumer";
    }

    @Override
    protected void handleMessage(ArticleTagsRefreshCacheMessage message) throws Exception {
        articleTagsService.initArticleTagsLocalCache();
    }

    @Override
    protected void overMaxRetryTimesMessage(ArticleTagsRefreshCacheMessage message) {

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
    public void onMessage(ArticleTagsRefreshCacheMessage message) {
        super.dispatch(message);
    }
}

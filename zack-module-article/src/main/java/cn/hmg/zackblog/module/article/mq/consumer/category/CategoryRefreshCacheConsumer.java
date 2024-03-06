package cn.hmg.zackblog.module.article.mq.consumer.category;

import cn.hmg.zackblog.framework.rocketmq.core.listener.AbstractRocketMQListener;
import cn.hmg.zackblog.module.article.mq.producer.category.CategoryRefreshCacheMessage;
import cn.hmg.zackblog.module.article.service.category.ICategoryService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static cn.hmg.zackblog.module.article.mq.topic.category.CategoryTopic.*;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-17 14:41
 * @description: 分类刷新缓存消费者
 */
@Component
@RocketMQMessageListener(topic = CATEGORY_REFRESH_CACHE, consumerGroup = CONSUMER_GROUP)
public class CategoryRefreshCacheConsumer extends AbstractRocketMQListener<CategoryRefreshCacheMessage> implements RocketMQListener<CategoryRefreshCacheMessage> {

    @Resource
    private ICategoryService categoryService;


    @Override
    protected String consumerName() {
        return "CategoryRefreshCacheConsumer";
    }

    @Override
    protected void handleMessage(CategoryRefreshCacheMessage message) throws Exception {
        categoryService.initCategoryLocalCache();
    }

    @Override
    protected void overMaxRetryTimesMessage(CategoryRefreshCacheMessage message) {

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
    public void onMessage(CategoryRefreshCacheMessage message) {
        super.dispatch(message);
    }
}

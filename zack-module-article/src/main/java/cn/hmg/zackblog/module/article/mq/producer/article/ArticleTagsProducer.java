package cn.hmg.zackblog.module.article.mq.producer.article;

import cn.hmg.zackblog.framework.rocketmq.core.RocketMQTemplateExt;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.UUID;

import static cn.hmg.zackblog.module.article.mq.topic.article.ArticleTagsTopic.*;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-17 11:15
 * @description: 文章标签生产者
 */
@Component
public class ArticleTagsProducer {
    @Resource
    private RocketMQTemplateExt rocketMQTemplateExt;

    public void asyncSendArticleTagsRefreshCacheMessage(){
        String topic = rocketMQTemplateExt.buildDestination(ARTICLE_TAGS_REFRESH_CACHE, TAG);
        ArticleTagsRefreshCacheMessage articleTagsRefreshCacheMessage = new ArticleTagsRefreshCacheMessage();
        articleTagsRefreshCacheMessage.setMessage("refresh article tags cache");
        articleTagsRefreshCacheMessage.setKey(UUID.randomUUID().toString());
        articleTagsRefreshCacheMessage.setSource("asyncSendArticleTagsRefreshCacheMessage");
        rocketMQTemplateExt.asyncSend(topic, articleTagsRefreshCacheMessage);
    }
}

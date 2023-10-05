package cn.hmg.zackblog.module.article.mq.topic.article;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-17 11:16
 * @description: 文章标签 topic
 */
public class ArticleTagsTopic {
    public static final String ARTICLE_TAGS_REFRESH_CACHE = "article_tags_refresh_cache";

    public static final String CONSUMER_GROUP = "article_tags_consumer_group";

    /**
     * tag以业务模块区分
     */
    public static final String TAG = "articles_article_tags";
}

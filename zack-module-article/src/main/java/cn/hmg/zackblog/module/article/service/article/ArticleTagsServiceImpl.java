package cn.hmg.zackblog.module.article.service.article;

import cn.hmg.zackblog.module.article.entity.article.ArticleTags;
import cn.hmg.zackblog.module.article.mapper.article.ArticleTagsMapper;
import cn.hmg.zackblog.module.article.mq.producer.article.ArticleTagsProducer;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static cn.hmg.zackblog.framework.common.utils.collections.CollectionUtils.*;

/**
 * <p>
 * 文章标签中间表 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-09-11
 */
@Slf4j
@Service
public class ArticleTagsServiceImpl extends ServiceImpl<ArticleTagsMapper, ArticleTags> implements IArticleTagsService {

    @Resource
    private ArticleTagsMapper articleTagsMapper;

    @Getter
    private volatile Map<Long, Set<Long>> articleTagsCache;

    @Resource
    private ArticleTagsProducer articleTagsProducer;

    @PostConstruct
    @Override
    public void initArticleTagsLocalCache() {
        List<ArticleTags> articleTags = articleTagsMapper.selectList();
        log.info("[initArticleTagsLocalCache] => 初始化文章标签缓存，数量为：{}", articleTags.size());
        articleTagsCache = convertMapByGrouping(articleTags, ArticleTags::getArticleId, ArticleTags::getTagId, Collectors.toSet());
    }

    @Override
    public Set<Long> selectTagIdsFromCacheByArticleId(Long articleId) {
        return articleTagsCache.getOrDefault(articleId, Collections.emptySet());
    }

    @Override
    public boolean verifyTagIsAssociatedWithArticle(Long articleId, Long tagId) {
        Set<Long> tagIds = articleTagsCache.getOrDefault(articleId, Collections.emptySet());
        return tagIds.contains(tagId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createArticleTagsBatch(List<ArticleTags> articleTagsList) {
        articleTagsMapper.insertBatch(articleTagsList);

        //事务提交后刷新缓存
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                articleTagsProducer.asyncSendArticleTagsRefreshCacheMessage();
            }
        });
    }

    @Override
    public Set<Long> selectTagIdsFromDbByArticleId(Long articleId) {
        List<ArticleTags> articleTagsList = articleTagsMapper.selectListByArticleId(articleId);
        return convertSet(articleTagsList, ArticleTags::getTagId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteArticleTags(Long articleId, Collection<Long> deleteTagIds) {
        articleTagsMapper.deleteArticleTags(articleId, deleteTagIds);

        //事务提交后刷新缓存
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                articleTagsProducer.asyncSendArticleTagsRefreshCacheMessage();
            }
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByArticleId(Long articleId) {
        articleTagsMapper.deleteByArticleId(articleId);

        //事务提交后刷新缓存
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                articleTagsProducer.asyncSendArticleTagsRefreshCacheMessage();
            }
        });
    }

    @Override
    public Long selectCountByTagId(Long tagId) {
        return articleTagsMapper.selectCountByTagId(tagId);
    }

}

package cn.hmg.zackblog.module.article.service.article;

import cn.hmg.zackblog.module.article.entity.article.ArticleTags;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 文章标签中间表 服务类
 * </p>
 *
 * @author hmg
 * @since 2023-09-11
 */
public interface IArticleTagsService extends IService<ArticleTags> {
    /**
     * 初始化文章标签本地缓存
     */
    void initArticleTagsLocalCache();

    /**
     * 从缓存中根据文章id查询标签ids
     * @param articleId 文章id
     * @return tagIds
     */
    Set<Long> selectTagIdsFromCacheByArticleId(Long articleId);

    /**
     * 校验标签是否与文章有绑定
     * @param articleId 文章id
     * @param tagId 标签id
     * @return true or false
     */
    boolean verifyTagIsAssociatedWithArticle(Long articleId, Long tagId);

    /**
     * 批量创建文章标签
     * @param articleTagsList 文章标签集合
     */
    void createArticleTagsBatch(List<ArticleTags> articleTagsList);

    /**
     * 从db中根据文章id查询标签集合
     * @param articleId 文章id
     * @return tagIds
     */
    Set<Long> selectTagIdsFromDbByArticleId(Long articleId);

    /**
     * 删除文章标签
     * @param articleId 文章id
     * @param deleteTagIds 删除的标签id集合
     */
    void deleteArticleTags(Long articleId, Collection<Long> deleteTagIds);

    /**
     * 根据文章id删除文章标签
     * @param articleId 文章id
     */
    void deleteByArticleId(Long articleId);

    /**
     * 根据tagId查询文章标签数量
     * @param tagId 标签id
     * @return article tags count
     */
    Long selectCountByTagId(Long tagId);
}

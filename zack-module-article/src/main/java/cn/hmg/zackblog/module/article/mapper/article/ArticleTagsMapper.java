package cn.hmg.zackblog.module.article.mapper.article;

import cn.hmg.zackblog.framework.mybatisplus.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.framework.mybatisplus.core.query.LambdaQueryWrapperExtend;
import cn.hmg.zackblog.module.article.entity.article.ArticleTags;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 文章标签中间表 Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-09-11
 */
@Mapper
public interface ArticleTagsMapper extends BaseMapperExtend<ArticleTags> {

    default List<ArticleTags> selectListByArticleId(Long articleId) {
        return selectList(new LambdaQueryWrapperExtend<ArticleTags>().eq(ArticleTags::getArticleId, articleId));
    }

    default void deleteArticleTags(Long articleId, Collection<Long> deleteTagIds) {
        delete(new LambdaQueryWrapperExtend<ArticleTags>().eq(ArticleTags::getArticleId, articleId)
                .in(ArticleTags::getTagId, deleteTagIds));
    }

    default void deleteByArticleId(Long articleId) {
        delete(ArticleTags::getArticleId, articleId);
    }

    default Long selectCountByTagId(Long tagId) {
        return selectCount(ArticleTags::getTagId, tagId);
    }
}

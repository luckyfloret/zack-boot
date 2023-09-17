package cn.hmg.zackblog.module.article.mapper.article;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.mybatisplus.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.framework.mybatisplus.core.query.LambdaQueryWrapperExtend;
import cn.hmg.zackblog.module.article.controller.admin.article.vo.ArticlePageReqVO;
import cn.hmg.zackblog.module.article.entity.article.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 文章管理 Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-09-11
 */
@Mapper
public interface ArticleMapper extends BaseMapperExtend<Article> {

    default PageResult<Article> getPage(ArticlePageReqVO reqVO) {
        return page(reqVO, new LambdaQueryWrapperExtend<Article>()
                .likeIfExists(Article::getTitle, reqVO.getTitle())
                .eqIfExists(Article::getCategoryId, reqVO.getCategoryId())
                .eqIfExists(Article::getIsPublish, reqVO.getIsPublish())
        );
    }

    default Long selectCountByCategoryId(Long categoryId) {
        return selectCount(Article::getCategoryId, categoryId);
    }
}

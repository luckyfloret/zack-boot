package cn.hmg.zackblog.module.article.convert.article;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.article.controller.admin.article.vo.ArticleCreateReqVO;
import cn.hmg.zackblog.module.article.controller.admin.article.vo.ArticlePageRespVO;
import cn.hmg.zackblog.module.article.controller.admin.article.vo.ArticleRespVO;
import cn.hmg.zackblog.module.article.controller.admin.article.vo.ArticleUpdateReqVO;
import cn.hmg.zackblog.module.article.entity.article.Article;
import cn.hmg.zackblog.module.article.service.article.dto.ArticlePageRespDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-14 11:00
 * @description:
 */
@Mapper
public interface ArticleConvert {
    ArticleConvert INSTANCE = Mappers.getMapper(ArticleConvert.class);

    PageResult<ArticlePageRespDTO> convert(PageResult<Article> pageResult);

    @Mapping(target = "tagVOList", source = "tagDTOList")
    ArticlePageRespVO convert(ArticlePageRespDTO articlePageRespDTO);

    PageResult<ArticlePageRespVO> convertArticlePageRespVO(PageResult<ArticlePageRespDTO> pageResult);

    ArticleRespVO convert(Article article);

    Article convert(ArticleCreateReqVO reqVO);

    Article convert(ArticleUpdateReqVO reqVO);
}

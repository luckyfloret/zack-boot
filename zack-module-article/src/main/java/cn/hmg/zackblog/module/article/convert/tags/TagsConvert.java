package cn.hmg.zackblog.module.article.convert.tags;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.article.controller.admin.article.vo.ArticlePageRespVO;
import cn.hmg.zackblog.module.article.controller.admin.tags.vo.*;
import cn.hmg.zackblog.module.article.entity.tags.Tags;
import cn.hmg.zackblog.module.article.service.article.dto.ArticlePageRespDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-15 20:48
 * @description:
 */
@Mapper
public interface TagsConvert {
    TagsConvert INSTANCE = Mappers.getMapper(TagsConvert.class);

    List<ArticlePageRespDTO.TagDTO> convert(List<Tags> tagsList);

    PageResult<TagsPageRespVO> convert(PageResult<Tags> pageResult);

    TagsRespVO convert(Tags tags);

    Tags convert(TagsCreateReqVO reqVO);

    Tags convert(TagsUpdateReqVO reqVO);

    List<TagsListSimpleRespVO> convertTagsListSimple(List<Tags> tagsList);
}

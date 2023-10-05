package cn.hmg.zackblog.module.article.mapper.tags;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.mybatisplus.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.framework.mybatisplus.core.query.LambdaQueryWrapperExtend;
import cn.hmg.zackblog.module.article.controller.admin.tags.vo.TagsPageReqVO;
import cn.hmg.zackblog.module.article.entity.category.Category;
import cn.hmg.zackblog.module.article.entity.tags.Tags;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 标签管理 Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-09-11
 */
@Mapper
public interface TagsMapper extends BaseMapperExtend<Tags> {

    default PageResult<Tags> getPage(TagsPageReqVO reqVO) {
        return page(reqVO, new LambdaQueryWrapperExtend<Tags>()
                .likeIfExists(Tags::getTagName, reqVO.getTagName())
                .orderByAsc(Tags::getSort)
        );
    }

    default Tags selectOneByTagName(String tagName) {
        return selectOne(Tags::getTagName, tagName);
    }
}

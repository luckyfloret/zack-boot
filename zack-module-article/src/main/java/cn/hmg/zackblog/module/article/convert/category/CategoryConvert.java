package cn.hmg.zackblog.module.article.convert.category;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.article.controller.admin.category.vo.CategoryCreateReqVO;
import cn.hmg.zackblog.module.article.controller.admin.category.vo.CategoryPageRespVO;
import cn.hmg.zackblog.module.article.controller.admin.category.vo.CategoryRespVO;
import cn.hmg.zackblog.module.article.controller.admin.category.vo.CategoryUpdateReqVO;
import cn.hmg.zackblog.module.article.entity.category.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-16 21:21
 * @description:
 */
@Mapper
public interface CategoryConvert {
    CategoryConvert INSTANCE = Mappers.getMapper(CategoryConvert.class);

    PageResult<CategoryPageRespVO> convert(PageResult<Category> pageResult);

    CategoryRespVO convert(Category category);

    Category convert(CategoryCreateReqVO reqVO);

    Category convert(CategoryUpdateReqVO reqVO);
}

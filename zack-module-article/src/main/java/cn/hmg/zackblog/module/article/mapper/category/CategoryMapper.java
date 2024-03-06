package cn.hmg.zackblog.module.article.mapper.category;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.mybatisplus.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.framework.mybatisplus.core.query.LambdaQueryWrapperExtend;
import cn.hmg.zackblog.module.article.controller.admin.category.vo.CategoryPageReqVO;
import cn.hmg.zackblog.module.article.entity.category.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 分类管理 Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-09-11
 */
@Mapper
public interface CategoryMapper extends BaseMapperExtend<Category> {
    default PageResult<Category> getPage(CategoryPageReqVO reqVO) {
        return page(reqVO, new LambdaQueryWrapperExtend<Category>()
                .likeIfExists(Category::getCategoryName, reqVO.getCategoryName())
                .orderByAsc(Category::getSort)
        );
    }

    default Category selectByCategoryName(String categoryName) {
        return selectOne(Category::getCategoryName, categoryName);
    }
}

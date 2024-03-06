package cn.hmg.zackblog.module.article.service.category;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.article.controller.admin.category.vo.CategoryCreateReqVO;
import cn.hmg.zackblog.module.article.controller.admin.category.vo.CategoryPageReqVO;
import cn.hmg.zackblog.module.article.controller.admin.category.vo.CategoryUpdateReqVO;
import cn.hmg.zackblog.module.article.entity.category.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 分类管理 服务类
 * </p>
 *
 * @author hmg
 * @since 2023-09-11
 */
public interface ICategoryService extends IService<Category> {
    /**
     * 初始化分类本地缓存
     */
    void initCategoryLocalCache();

    /**
     * 从缓存中根据id获取分类
     * @param categoryId 分类id
     * @return category
     */
    Category getCategoryFromCacheById(Long categoryId);

    /**
     * 获取分类分页列表
     * @param reqVO 请求参数
     * @return category page
     */
    PageResult<Category> getPage(CategoryPageReqVO reqVO);

    /**
     * 根据id获取分类
     * @param id 分类id
     * @return category
     */
    Category getCategoryById(Long id);

    /**
     * 创建分类
     * @param reqVO 请求参数
     */
    void createCategory(CategoryCreateReqVO reqVO);

    /**
     * 更新分类
     * @param reqVO 更新分类
     */
    void updateCategory(CategoryUpdateReqVO reqVO);

    /**
     * 根据id删除分类
     * @param id 分类id
     */
    void deleteCategoryById(Long id);
}

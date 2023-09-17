package cn.hmg.zackblog.module.article.service.category;

import cn.hmg.zackblog.framework.common.exception.BusinessException;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.article.controller.admin.category.vo.CategoryCreateReqVO;
import cn.hmg.zackblog.module.article.controller.admin.category.vo.CategoryPageReqVO;
import cn.hmg.zackblog.module.article.controller.admin.category.vo.CategoryUpdateReqVO;
import cn.hmg.zackblog.module.article.convert.category.CategoryConvert;
import cn.hmg.zackblog.module.article.entity.category.Category;
import cn.hmg.zackblog.module.article.mapper.category.CategoryMapper;
import cn.hmg.zackblog.module.article.mq.producer.category.CategoryProducer;
import cn.hmg.zackblog.module.article.service.article.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static cn.hmg.zackblog.framework.common.utils.collections.CollectionUtils.convertMap;
import static cn.hmg.zackblog.module.article.enums.ErrorCodeEnum.*;

/**
 * <p>
 * 分类管理 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-09-11
 */
@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {


    @Getter
    private volatile Map<Long, Category> categoryCache;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private IArticleService articleService;

    @Resource
    private CategoryProducer categoryProducer;

    @PostConstruct
    @Override
    public void initCategoryLocalCache() {
        List<Category> categories = categoryMapper.selectList();
        log.info("[initCategoryLocalCache] => 初始化分类缓存，数量为：{}", categories.size());
        categoryCache = convertMap(categories, Category::getId);
    }

    @Override
    public Category getCategoryFromCacheById(Long categoryId) {
        return categoryCache.get(categoryId);
    }

    @Override
    public PageResult<Category> getPage(CategoryPageReqVO reqVO) {
        return categoryMapper.getPage(reqVO);
    }

    @Override
    public Category getCategoryById(Long id) {
        return verifyCategoryIsExists(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createCategory(CategoryCreateReqVO reqVO) {
        //校验分类名是否唯一
        verifyCategoryNameIsUnique(null, reqVO.getCategoryName());

        //执行db
        categoryMapper.insert(CategoryConvert.INSTANCE.convert(reqVO));

        //事务提交后刷新缓存
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                categoryProducer.asyncSendCategoryRefreshCacheMessage();
            }
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCategory(CategoryUpdateReqVO reqVO) {
        //校验分类名称是否唯一
        verifyCategoryNameIsUnique(reqVO.getId(), reqVO.getCategoryName());

        //执行db
        categoryMapper.updateById(CategoryConvert.INSTANCE.convert(reqVO));

        //事务提交后刷新缓存
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                categoryProducer.asyncSendCategoryRefreshCacheMessage();
            }
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCategoryById(Long id) {
        //校验分类是否存在
        verifyCategoryIsExists(id);

        //校验是否绑定有文章
        verifyCategoryIsBoundArticle(id);

        //执行db
        categoryMapper.deleteById(id);

        //事务提交后刷新缓存
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                categoryProducer.asyncSendCategoryRefreshCacheMessage();
            }
        });
    }

    /**
     * 校验分类是否与文章绑定
     * @param id 分类id
     */
    private void verifyCategoryIsBoundArticle(Long id) {
        if (articleService.selectCountByCategoryId(id) > 0) {
            throw new BusinessException(CATEGORY_ALREADY_BOUND_ARTICLE.getCode(), CATEGORY_ALREADY_BOUND_ARTICLE.getMessage());
        }
    }

    /**
     * 校验分类是否存在
     * @param id 分类id
     * @return category
     */
    private Category verifyCategoryIsExists(Long id) {
        Category category = categoryMapper.selectById(id);
        if (Objects.isNull(category)) {
            throw new BusinessException(CATEGORY_NOT_EXISTS.getCode(), CATEGORY_NOT_EXISTS.getMessage());
        }
        return category;
    }

    /**
     * 校验分类名称是否唯一
     * @param id 分类id
     * @param categoryName 分类名称
     */
    private void verifyCategoryNameIsUnique(Long id, String categoryName) {
        Category category = categoryMapper.selectByCategoryName(categoryName);
        if (Objects.isNull(category)) {
            return;
        }

        if (!category.getId().equals(id)) {
            throw new BusinessException(CATEGORY_ALREADY_EXISTS.getCode(), CATEGORY_ALREADY_EXISTS.getMessage());
        }
    }
}

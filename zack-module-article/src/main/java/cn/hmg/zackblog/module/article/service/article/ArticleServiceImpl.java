package cn.hmg.zackblog.module.article.service.article;

import cn.hmg.zackblog.framework.common.exception.BusinessException;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.common.utils.collections.CollectionUtils;
import cn.hmg.zackblog.module.article.controller.admin.article.vo.ArticleCreateReqVO;
import cn.hmg.zackblog.module.article.controller.admin.article.vo.ArticlePageReqVO;
import cn.hmg.zackblog.module.article.controller.admin.article.vo.ArticleUpdateReqVO;
import cn.hmg.zackblog.module.article.convert.article.ArticleConvert;
import cn.hmg.zackblog.module.article.convert.tags.TagsConvert;
import cn.hmg.zackblog.module.article.entity.article.Article;
import cn.hmg.zackblog.module.article.entity.article.ArticleTags;
import cn.hmg.zackblog.module.article.entity.category.Category;
import cn.hmg.zackblog.module.article.entity.tags.Tags;
import cn.hmg.zackblog.module.article.mapper.article.ArticleMapper;
import cn.hmg.zackblog.module.article.service.article.dto.ArticlePageRespDTO;
import cn.hmg.zackblog.module.article.service.category.ICategoryService;
import cn.hmg.zackblog.module.article.service.tags.ITagsService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static cn.hmg.zackblog.module.article.constants.CommonConstant.MAX_TAG_COUNT;
import static cn.hmg.zackblog.module.article.enums.ArticlePublishStatusEnum.verifyPublishStatusIsExists;
import static cn.hmg.zackblog.module.article.enums.ArticleRecommendStatusEnum.verifyRecommendStatusIsExists;
import static cn.hmg.zackblog.module.article.enums.ArticleTypeEnum.verifyTypeIsExists;
import static cn.hmg.zackblog.module.article.enums.ErrorCodeEnum.*;

/**
 * <p>
 * 文章管理 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-09-11
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {
    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ICategoryService categoryService;

    @Resource
    private IArticleTagsService articleTagsService;

    @Resource
    private ITagsService tagsService;

    @Override
    public PageResult<ArticlePageRespDTO> getPage(ArticlePageReqVO reqVO) {
        //获取文章分页
        PageResult<ArticlePageRespDTO> pageResult = ArticleConvert.INSTANCE.convert(articleMapper.getPage(reqVO));

        //组装分类数据
        pageResult.getData().forEach(articlePageRespVO -> {
            Category category = categoryService.getCategoryFromCacheById(articlePageRespVO.getCategoryId());
            articlePageRespVO.setCategoryName(category.getCategoryName());
        });

        //拿到分页数据的所有文章id
        Set<Long> articleIds = CollectionUtils.convertSet(pageResult.getData(), ArticlePageRespDTO::getId);
        Map<Long, List<ArticlePageRespDTO.TagDTO>> tagsMap = new HashMap<>(64);
        Set<Long> finalArticleIds = new HashSet<>();

        //过滤不符合条件的文章id
        articleIds.forEach(articleId -> {
            if (Objects.nonNull(reqVO.getTagId())) {
                if (articleTagsService.verifyTagIsAssociatedWithArticle(articleId, reqVO.getTagId())) {
                    finalArticleIds.add(articleId);
                }
                return;
            }
            finalArticleIds.add(articleId);
        });

        //根据最终的文章id获取到tagList并添加到map中
        finalArticleIds.forEach(articleId -> {
            Set<Long> tagsIds = articleTagsService.selectTagIdsFromCacheByArticleId(articleId);
            tagsMap.put(articleId, TagsConvert.INSTANCE.convert(tagsService.selectListFromCacheByIds(tagsIds)));
        });

        //组装最后结果，创建新的list，用于过滤pageResult不必要的数据
        List<ArticlePageRespDTO> result = new ArrayList<>();
        pageResult.getData().forEach(articlePageRespDTO -> {
            List<ArticlePageRespDTO.TagDTO> tagDTOList = tagsMap.get(articlePageRespDTO.getId());
            if (result.size() < finalArticleIds.size()) {
                articlePageRespDTO.setTagDTOList(tagDTOList);
                result.add(articlePageRespDTO);
            }
        });

        return new PageResult<>(result, (long) result.size());
    }

    @Override
    public Article getArticleById(Long id) {
        return verifyArticleIsExists(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createArticle(ArticleCreateReqVO reqVO) {
        //校验文章类型、是否发布、是否推荐等字段是否非法
        verifyArticle(reqVO.getType(), reqVO.getIsRecommend(), reqVO.getIsPublish());

        //校验标签是否存在、数量等
        verifyTag(reqVO.getTagIds());

        //校验分类是否存在
        verifyCategoryIsExists(reqVO.getCategoryId());

        Article article = ArticleConvert.INSTANCE.convert(reqVO);
        List<ArticleTags> articleTagsList = reqVO.getTagIds().stream().map(tagId -> {
            ArticleTags articleTags = new ArticleTags();
            articleTags.setArticleId(article.getId());
            articleTags.setTagId(tagId);
            return articleTags;
        }).collect(Collectors.toList());


        //执行db
        articleMapper.insert(article);
        articleTagsService.createArticleTagsBatch(articleTagsList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateArticle(ArticleUpdateReqVO reqVO) {
        //校验是否存在
        verifyArticleIsExists(reqVO.getId());

        //校验文章类型、是否发布、是否推荐等字段是否非法
        verifyArticle(reqVO.getType(), reqVO.getIsRecommend(), reqVO.getIsPublish());

        //校验标签
        verifyTag(reqVO.getTagIds());

        //校验分类是否存在
        verifyCategoryIsExists(reqVO.getCategoryId());

        //执行db
        articleMapper.insert(ArticleConvert.INSTANCE.convert(reqVO));

        //根据文章id从db获取所有tagId
        Set<Long> fromDbTagIds = articleTagsService.selectTagIdsFromDbByArticleId(reqVO.getId());

        //计算差集
        Collection<Long> createTagIds = CollectionUtil.subtract(reqVO.getTagIds(), fromDbTagIds);
        Collection<Long> deleteTagIds = CollectionUtil.subtract(fromDbTagIds, reqVO.getTagIds());

        //新增逻辑
        if (!createTagIds.isEmpty()) {
            List<ArticleTags> articleTagsList = createTagIds.stream().map(createTagId -> {
                ArticleTags articleTags = new ArticleTags();
                articleTags.setArticleId(reqVO.getId());
                articleTags.setTagId(createTagId);
                return articleTags;
            }).collect(Collectors.toList());
            articleTagsService.createArticleTagsBatch(articleTagsList);
        }

        //删除逻辑
        if (!deleteTagIds.isEmpty()) {
            articleTagsService.deleteArticleTags(reqVO.getId(), deleteTagIds);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteArticleById(Long id) {
        //校验文章是否存在
        verifyArticleIsExists(id);

        //执行db
        articleMapper.deleteById(id);
        articleTagsService.deleteByArticleId(id);
    }

    @Override
    public Long selectCountByCategoryId(Long categoryId) {
        return articleMapper.selectCountByCategoryId(categoryId);
    }

    /**
     * 校验分类是否存在
     * @param categoryId 分类id
     */
    private void verifyCategoryIsExists(Long categoryId) {
        categoryService.getCategoryById(categoryId);
    }

    /**
     *  校验标签
     * @param tagIds 标签ids
     */
    private void verifyTag(Set<Long> tagIds) {
        //校验标签数量
        if (tagIds.size() > MAX_TAG_COUNT) {
            throw new BusinessException(ARTICLE_BINDING_TAG_COUNT_ERROR.getCode(), ARTICLE_BINDING_TAG_COUNT_ERROR.getMessage());
        }

        //校验标签是否存在
        List<Tags> tagsList = tagsService.selectListFromCacheByIds(tagIds);
        if (tagsList.size() != tagIds.size()) {
            throw new BusinessException(TAGS_NOT_EXISTS.getCode(), TAGS_NOT_EXISTS.getMessage());
        }
    }

    /**
     * 校验文章是否存在
     * @param id 文章id
     * @return article
     */
    private Article verifyArticleIsExists(Long id) {
        Article article = articleMapper.selectById(id);
        if (Objects.isNull(article)) {
            throw new BusinessException(ARTICLE_NOT_EXISTS.getCode(), ARTICLE_NOT_EXISTS.getMessage());
        }
        return article;
    }

    /**
     * 校验文章
     * @param type 文章类型
     * @param isRecommend 推荐状态
     * @param isPublish 发布状态
     */
    private void verifyArticle(Integer type, Integer isRecommend, Integer isPublish) {
        //校验文章类型
        verifyArticleType(type);

        //校验推荐状态
        verifyArticleRecommend(isRecommend);

        //校验发布状态
        verifyArticlePublish(isPublish);
    }

    /**
     * 校验文章发布状态是否合法
     * @param isPublish 发布状态
     */
    private void verifyArticlePublish(Integer isPublish) {
        Assert.isTrue(verifyPublishStatusIsExists(isPublish), () -> new BusinessException(
                ARTICLE_PUBLISH_STATUS_NOT_EXISTS.getCode(), ARTICLE_PUBLISH_STATUS_NOT_EXISTS.getMessage()));
    }

    /**
     *  校验文章推荐状态是否合法
     * @param isRecommend 推荐状态
     */
    private void verifyArticleRecommend(Integer isRecommend) {
        Assert.isTrue(verifyRecommendStatusIsExists(isRecommend), () -> new BusinessException(
                ARTICLE_RECOMMEND_STATUS_NOT_EXISTS.getCode(), ARTICLE_RECOMMEND_STATUS_NOT_EXISTS.getMessage()));
    }

    /**
     * 校验文章类型
     * @param type 文章类型
     */
    private void verifyArticleType(Integer type) {
        Assert.isTrue(verifyTypeIsExists(type), () -> new BusinessException(
                ARTICLE_TYPE_NOT_EXISTS.getCode(), ARTICLE_TYPE_NOT_EXISTS.getMessage()));
    }
}

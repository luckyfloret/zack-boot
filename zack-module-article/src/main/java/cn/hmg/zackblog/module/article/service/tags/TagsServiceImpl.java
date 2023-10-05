package cn.hmg.zackblog.module.article.service.tags;

import cn.hmg.zackblog.framework.common.exception.BusinessException;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.article.controller.admin.tags.vo.TagsCreateReqVO;
import cn.hmg.zackblog.module.article.controller.admin.tags.vo.TagsPageReqVO;
import cn.hmg.zackblog.module.article.controller.admin.tags.vo.TagsUpdateReqVO;
import cn.hmg.zackblog.module.article.convert.tags.TagsConvert;
import cn.hmg.zackblog.module.article.entity.tags.Tags;
import cn.hmg.zackblog.module.article.mapper.tags.TagsMapper;
import cn.hmg.zackblog.module.article.mq.producer.tags.TagsProducer;
import cn.hmg.zackblog.module.article.service.article.IArticleTagsService;
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
import java.util.Set;
import java.util.stream.Collectors;

import static cn.hmg.zackblog.framework.common.utils.collections.CollectionUtils.convertMap;
import static cn.hmg.zackblog.module.article.enums.ErrorCodeEnum.*;

/**
 * <p>
 * 标签管理 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-09-11
 */
@Service
@Slf4j
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements ITagsService {

    @Resource
    private TagsMapper tagsMapper;

    @Getter
    private volatile Map<Long, Tags> tagsCache;

    @Resource
    private IArticleTagsService articleTagsService;

    @Resource
    private TagsProducer tagsProducer;

    @PostConstruct
    @Override
    public void initTagsLocalCache() {
        List<Tags> tagsList = tagsMapper.selectList();
        log.info("[initTagsLocalCache] => 初始化标签缓存，数量为：{}", tagsList.size());
        tagsCache = convertMap(tagsList, Tags::getId);
    }

    @Override
    public List<Tags> selectListFromCacheByIds(Set<Long> tagsIds) {
        return tagsCache.values().stream().filter(tags -> tagsIds.contains(tags.getId())).collect(Collectors.toList());
    }

    @Override
    public PageResult<Tags> getPage(TagsPageReqVO reqVO) {
        return tagsMapper.getPage(reqVO);
    }

    @Override
    public Tags getTagsById(Long id) {
        return verifyTagsIsExists(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createTags(TagsCreateReqVO reqVO) {
        //校验标签名称是否唯一
        verifyTagsIsUnique(null, reqVO.getTagName());

        //执行db
        tagsMapper.insert(TagsConvert.INSTANCE.convert(reqVO));

        //事务提交后刷新缓存
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                tagsProducer.syncSendTagsRefreshCacheMessage();
            }
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateTags(TagsUpdateReqVO reqVO) {
        //校验标签是否存在
        verifyTagsIsExists(reqVO.getId());

        //校验标签名称是否唯一
        verifyTagsIsUnique(reqVO.getId(), reqVO.getTagName());

        //执行db
        tagsMapper.updateById(TagsConvert.INSTANCE.convert(reqVO));

        //事务提交后刷新缓存
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                tagsProducer.syncSendTagsRefreshCacheMessage();
            }
        });
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteTagsById(Long id) {
        //校验标签名称是否唯一
        verifyTagsIsExists(id);

        //校验标签是否被文章绑定
        verifyTagsIsBoundArticle(id);

        //执行db
        tagsMapper.deleteById(id);

        //事务提交后刷新缓存
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                tagsProducer.syncSendTagsRefreshCacheMessage();
            }
        });
    }

    /**
     * 校验标签是否绑定有文章
     * @param id 标签id
     */
    private void verifyTagsIsBoundArticle(Long id) {
        if (articleTagsService.selectCountByTagId(id) > 0) {
            throw new BusinessException(TAGS_ALREADY_BOUND_ARTICLE.getCode(), TAGS_ALREADY_BOUND_ARTICLE.getMessage());
        }
    }

    /**
     * 校验标签是否存在
     * @param id 标签id
     * @return tags
     */
    private Tags verifyTagsIsExists(Long id) {
        Tags tags = tagsMapper.selectById(id);

        if (Objects.isNull(tags)) {
            throw new BusinessException(TAGS_NOT_EXISTS.getCode(), TAGS_NOT_EXISTS.getMessage());
        }
        return tags;
    }

    /**
     * 校验标签是否唯一
     * @param id 标签id
     * @param tagName 标签名称
     */
    private void verifyTagsIsUnique(Long id, String tagName) {
        Tags tags = tagsMapper.selectOneByTagName(tagName);
        if (Objects.isNull(tags)) {
            return;
        }

        if (!tags.getId().equals(id)) {
            throw new BusinessException(TAGS_ALREADY_EXISTS.getCode(), TAGS_ALREADY_EXISTS.getMessage());
        }
    }
}

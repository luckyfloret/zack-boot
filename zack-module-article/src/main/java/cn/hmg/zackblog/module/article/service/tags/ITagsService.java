package cn.hmg.zackblog.module.article.service.tags;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.article.controller.admin.tags.vo.TagsCreateReqVO;
import cn.hmg.zackblog.module.article.controller.admin.tags.vo.TagsPageReqVO;
import cn.hmg.zackblog.module.article.controller.admin.tags.vo.TagsUpdateReqVO;
import cn.hmg.zackblog.module.article.entity.tags.Tags;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 标签管理 服务类
 * </p>
 *
 * @author hmg
 * @since 2023-09-11
 */
public interface ITagsService extends IService<Tags> {
    /**
     * 初始化标签本地缓存
     */
    void initTagsLocalCache();

    /**
     * 从缓存中根据标签ids查询标签列表
     * @param tagsIds 标签id集合
     * @return tagList
     */
    List<Tags> selectListFromCacheByIds(Set<Long> tagsIds);

    /**
     * 获取标签分页列表
     * @param reqVO 请求参数
     * @return 标签分页列表
     */
    PageResult<Tags> getPage(TagsPageReqVO reqVO);

    /**
     * 根据id获取标签
     * @param id 标签id
     * @return tags
     */
    Tags getTagsById(Long id);

    /**
     * 创建标签
     * @param reqVO 请求参数
     */
    void createTags(TagsCreateReqVO reqVO);

    /**
     * 更新标签
     * @param reqVO 请求参数
     */
    void updateTags(TagsUpdateReqVO reqVO);

    /**
     * 根据id删除标签
     * @param id 标签id
     */
    void deleteTagsById(Long id);
}

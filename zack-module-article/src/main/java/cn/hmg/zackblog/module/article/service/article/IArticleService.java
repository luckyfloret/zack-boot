package cn.hmg.zackblog.module.article.service.article;

import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.article.controller.admin.article.vo.ArticleCreateReqVO;
import cn.hmg.zackblog.module.article.controller.admin.article.vo.ArticlePageReqVO;
import cn.hmg.zackblog.module.article.controller.admin.article.vo.ArticleUpdateReqVO;
import cn.hmg.zackblog.module.article.entity.article.Article;
import cn.hmg.zackblog.module.article.service.article.dto.ArticlePageRespDTO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 文章管理 服务类
 * </p>
 *
 * @author hmg
 * @since 2023-09-11
 */
public interface IArticleService extends IService<Article> {
    /**
     * 获取后台文章分页
     * @param reqVO 请求参数
     * @return 文章分页列表
     */
    PageResult<ArticlePageRespDTO> getPage(ArticlePageReqVO reqVO);

    /**
     * 根据id获取文章
     * @param id 文章id
     * @return article
     */
    Article getArticleById(Long id);

    /**
     * 创建文章
     * @param reqVO 请求参数
     */
    void createArticle(ArticleCreateReqVO reqVO);

    /**
     * 更新文章
     * @param reqVO 请求参数
     */
    void updateArticle(ArticleUpdateReqVO reqVO);

    /**
     * 根据id删除文章
     * @param id 文章id
     */
    void deleteArticleById(Long id);

    /**
     * 根据分类id查询绑定的文章数量
     * @param categoryId 分类id
     * @return 绑定分类的文章数量
     */
    Long selectCountByCategoryId(Long categoryId);
}

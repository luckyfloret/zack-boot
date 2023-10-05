package cn.hmg.zackblog.module.article.controller.admin.article;

import cn.hmg.zackblog.framework.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.operatelog.core.annotation.OperateLog;
import cn.hmg.zackblog.module.article.controller.admin.article.vo.*;
import cn.hmg.zackblog.module.article.convert.article.ArticleConvert;
import cn.hmg.zackblog.module.article.service.article.IArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static cn.hmg.zackblog.framework.common.pojo.CommonResult.success;
import static cn.hmg.zackblog.framework.operatelog.core.enums.OperateLogTypeEnum.*;

/**
 * <p>
 * 文章管理 前端控制器
 * </p>
 *
 * @author hmg
 * @since 2023-09-11
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "后台-文章管理")
@RequestMapping("/admin/articles/article")
public class ArticleController {

    private final IArticleService articleService;

    @GetMapping("/page")
    @Operation(summary = "文章分页列表")
    @PreAuthorize("@spe.hasPermission('articles:article:list')")
    public CommonResult<PageResult<ArticlePageRespVO>> page(@Valid ArticlePageReqVO reqVO) {
        return success(ArticleConvert.INSTANCE.convertArticlePageRespVO(articleService.getPage(reqVO)));
    }


    @GetMapping("/get/{id}")
    @Operation(summary = "根据id获取文章")
    @PreAuthorize("@spe.hasPermission('articles:article:query')")
    public CommonResult<ArticleRespVO> getArticleById(@PathVariable("id") Long id){
        return success(ArticleConvert.INSTANCE.convert(articleService.getArticleById(id)));
    }


    @PostMapping("/create")
    @Operation(summary = "创建文章")
    @PreAuthorize("@spe.hasPermission('articles:article:query')")
    @OperateLog(operateName = "创建文章", operateType = CREATE)
    public CommonResult<Boolean> createArticle(@Valid @RequestBody ArticleCreateReqVO reqVO){
        articleService.createArticle(reqVO);
        return success(true);
    }

    @PutMapping("/update")
    @Operation(summary = "更新文章")
    @PreAuthorize("@spe.hasPermission('articles:article:update')")
    @OperateLog(operateName = "更新文章", operateType = UPDATE)
    public CommonResult<Boolean> updateArticle(@Valid @RequestBody ArticleUpdateReqVO reqVO){
        articleService.updateArticle(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "根据id删除文章")
    @PreAuthorize("@spe.hasPermission('articles:article:delete')")
    @OperateLog(operateName = "根据id删除文章", operateType = DELETE)
    public CommonResult<Boolean> deleteArticleById(@PathVariable("id") Long id){
        articleService.deleteArticleById(id);
        return success(true);
    }

}

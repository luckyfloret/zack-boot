package cn.hmg.zackblog.module.article.controller.admin.category;

import cn.hmg.zackblog.framework.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.operatelog.core.annotation.OperateLog;
import cn.hmg.zackblog.module.article.controller.admin.category.vo.*;
import cn.hmg.zackblog.module.article.convert.category.CategoryConvert;
import cn.hmg.zackblog.module.article.service.category.ICategoryService;
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
 * 分类管理 前端控制器
 * </p>
 *
 * @author hmg
 * @since 2023-09-11
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "后台-分类管理")
@RequestMapping("/admin/article/category")
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping("/page")
    @Operation(summary = "分类分页列表")
    @PreAuthorize("@spe.hasPermission('articles:category:list')")
    public CommonResult<PageResult<CategoryPageRespVO>> page(@Valid CategoryPageReqVO reqVO) {
        return success(CategoryConvert.INSTANCE.convert(categoryService.getPage(reqVO)));
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "根据id获取分类")
    @PreAuthorize("@spe.hasPermission('articles:category:query')")
    public CommonResult<CategoryRespVO> getCategoryById(@PathVariable("id") Long id) {
        return success(CategoryConvert.INSTANCE.convert(categoryService.getCategoryById(id)));
    }


    @PostMapping("/create")
    @Operation(summary = "创建分类")
    @PreAuthorize("@spe.hasPermission('articles:category:create')")
    @OperateLog(operateName = "创建分类", operateType = CREATE)
    public CommonResult<Boolean> createCategory(@Valid @RequestBody CategoryCreateReqVO reqVO) {
        categoryService.createCategory(reqVO);
        return success(true);
    }

    @PutMapping("/update")
    @Operation(summary = "更新分类")
    @PreAuthorize("@spe.hasPermission('articles:category:update')")
    @OperateLog(operateName = "更新分类", operateType = UPDATE)
    public CommonResult<Boolean> updateCategory(@Valid @RequestBody CategoryUpdateReqVO reqVO) {
        categoryService.updateCategory(reqVO);
        return success(true);
    }

    @PutMapping("/delete/{id}")
    @Operation(summary = "删除分类")
    @PreAuthorize("@spe.hasPermission('articles:category:delete')")
    @OperateLog(operateName = "删除分类", operateType = DELETE)
    public CommonResult<Boolean> deleteCategoryById(@PathVariable("id") Long id) {
        categoryService.deleteCategoryById(id);
        return success(true);
    }
}

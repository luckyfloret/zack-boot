package cn.hmg.zackblog.module.article.controller.admin.tags;

import cn.hmg.zackblog.framework.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.operatelog.core.annotation.OperateLog;
import cn.hmg.zackblog.framework.operatelog.core.enums.OperateLogTypeEnum;
import cn.hmg.zackblog.module.article.controller.admin.tags.vo.*;
import cn.hmg.zackblog.module.article.convert.tags.TagsConvert;
import cn.hmg.zackblog.module.article.service.tags.ITagsService;
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
 * 标签管理 前端控制器
 * </p>
 *
 * @author hmg
 * @since 2023-09-11
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "后台-标签管理")
@RequestMapping("/admin/articles/tags")
public class TagsController {
    private final ITagsService tagsService;

    @GetMapping("/page")
    @Operation(summary = "标签分页列表")
    @PreAuthorize("@spe.hasPermission('articles:tags:list')")
    public CommonResult<PageResult<TagsPageRespVO>> page(@Valid TagsPageReqVO reqVO) {
        return success(TagsConvert.INSTANCE.convert(tagsService.getPage(reqVO)));
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "标签分页列表")
    @PreAuthorize("@spe.hasPermission('articles:tags:query')")
    public CommonResult<TagsRespVO> getTagsById(@PathVariable("id") Long id) {
        return success(TagsConvert.INSTANCE.convert(tagsService.getTagsById(id)));
    }

    @PostMapping("/create")
    @Operation(summary = "创建标签")
    @OperateLog(operateName = "创建标签", operateType = CREATE)
    @PreAuthorize("@spe.hasPermission('articles:tags:create')")
    public CommonResult<Boolean> createTags(@Valid @RequestBody TagsCreateReqVO reqVO) {
        tagsService.createTags(reqVO);
        return success(true);
    }

    @PutMapping("/update")
    @Operation(summary = "更新标签")
    @OperateLog(operateName = "更新标签", operateType = UPDATE)
    @PreAuthorize("@spe.hasPermission('articles:tags:update')")
    public CommonResult<Boolean> updateTags(@Valid @RequestBody TagsUpdateReqVO reqVO) {
        tagsService.updateTags(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除标签")
    @OperateLog(operateName = "删除标签", operateType = DELETE)
    @PreAuthorize("@spe.hasPermission('articles:tags:delete')")
    public CommonResult<Boolean> deleteTagsById(@PathVariable("id") Long id) {
        tagsService.deleteTagsById(id);
        return success(true);
    }
}

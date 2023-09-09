package cn.hmg.zackblog.module.system.controller.admin.dict;

import cn.hmg.zackblog.framework.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.operatelog.core.annotation.OperateLog;
import cn.hmg.zackblog.module.system.controller.admin.dict.vo.type.*;
import cn.hmg.zackblog.module.system.convert.dict.DictTypeConvert;
import cn.hmg.zackblog.module.system.entity.dict.DictType;
import cn.hmg.zackblog.module.system.service.dict.IDictTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static cn.hmg.zackblog.framework.common.pojo.CommonResult.success;
import static cn.hmg.zackblog.framework.operatelog.core.enums.OperateLogTypeEnum.*;

/**
 * <p>
 * 字典类型 前端控制器
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Tag(name = "后台-字典管理-字典类型")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/system/dict-type")
public class DictTypeController {
    private final IDictTypeService dictTypeService;

    @GetMapping("/page")
    @PreAuthorize("@spe.hasPermission('system:dict:list')")
    @Operation(summary = "字典类型分页")
    @OperateLog(operateName = "字典类型分页", operateType = QUERY)
    public CommonResult<PageResult<DictTypePageRespVO>> page(@Valid DictTypePageReqVO dictTypePageReqVO){
        PageResult<DictType> dictTypePageResult = dictTypeService.getPage(dictTypePageReqVO);
        return success(DictTypeConvert.INSTANCE.convert(dictTypePageResult));
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("@spe.hasPermission('system:dict:query')")
    @Operation(summary = "根据id获取字典类型")
    @OperateLog(operateName = "根据id获取字典类型", operateType = QUERY)
    public CommonResult<DictTypeRespVO> getDictTypeById(@PathVariable("id") Long id){
        return success(DictTypeConvert.INSTANCE.convert(dictTypeService.getDictTypeById(id)));
    }

    @PostMapping("/create")
    @PreAuthorize("@spe.hasPermission('system:dict:create')")
    @Operation(summary = "创建字典类型")
    @OperateLog(operateName = "创建字典类型", operateType = CREATE)
    public CommonResult<Boolean> createDictType(@Valid @RequestBody DictTypeCreateReqVO dictTypeCreateReqVO){
        dictTypeService.createDictType(dictTypeCreateReqVO);
        return success(true);
    }

    @PutMapping("/update")
    @PreAuthorize("@spe.hasPermission('system:dict:update')")
    @Operation(summary = "更新字典类型")
    @OperateLog(operateName = "更新字典类型", operateType = UPDATE)
    public CommonResult<Boolean> updateDictType(@Valid @RequestBody DictTypeUpdateReqVO dictTypeUpdateReqVO){
        dictTypeService.updateDictType(dictTypeUpdateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("@spe.hasPermission('system:dict:delete')")
    @Operation(summary = "删除字典类型")
    @OperateLog(operateName = "删除字典类型", operateType = DELETE)
    public CommonResult<Boolean> deleteDictType(@PathVariable("id") Long id){
        dictTypeService.deleteDictTypeById(id);
        return success(true);
    }

    @GetMapping("/list")
    @PreAuthorize("@spe.hasPermission('system:dict:list')")
    @Operation(summary = "字典类型列表， 用于字典数据选项时")
    @OperateLog(operateName = "字典类型列表", operateType = QUERY)
    public CommonResult<List<DictTypeListRespVO>> list() {
        return success(DictTypeConvert.INSTANCE.convert(dictTypeService.getDictTypeList()));
    }

    @GetMapping("/export")
    @Operation(summary = "导出字典类型")
    public void exportExcel(){

    }
}

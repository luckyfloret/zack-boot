package cn.hmg.zackblog.module.system.controller.admin.dict;

import cn.hmg.zackblog.framework.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.operatelog.core.annotation.OperateLog;
import cn.hmg.zackblog.module.system.controller.admin.dict.vo.data.*;
import cn.hmg.zackblog.module.system.convert.dict.DictDataConvert;
import cn.hmg.zackblog.module.system.entity.dict.DictData;
import cn.hmg.zackblog.module.system.service.dict.IDictDataService;
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
 * 数据字典 前端控制器
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Tag(name = "后台-字典管理-字典数据")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/system/dict-data")
public class DictDataController {

    private final IDictDataService dictDataService;

    @GetMapping("/page")
    @Operation(summary = "字典数据分页")
    @PreAuthorize("@spe.hasPermission('system:dict:list')")
    @OperateLog(operateName = "字典数据分页", operateType = QUERY)
    public CommonResult<PageResult<DictDataPageRespVO>> getPage(@Valid DictDataPageReqVO dictDataPageReqVO) {
        return success(DictDataConvert.INSTANCE.convertPage(dictDataService.getPage(dictDataPageReqVO)));
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "根据id获取字典数据")
    @PreAuthorize("@spe.hasPermission('system:dict:query')")
    @OperateLog(operateName = "根据id获取字典数据", operateType = QUERY)
    public CommonResult<DictDataRespVO> getDictDataById(@PathVariable("id") Long id) {
        return success(DictDataConvert.INSTANCE.convert(dictDataService.getDictDataById(id)));
    }

    @PostMapping("/create")
    @Operation(summary = "创建字典数据")
    @PreAuthorize("@spe.hasPermission('system:dict:create')")
    @OperateLog(operateName = "创建字典数据", operateType = CREATE)
    public CommonResult<Boolean> createDictData(@Valid @RequestBody DictDataCreateReqVO dictDataCreateReqVO) {
        dictDataService.createDictData(dictDataCreateReqVO);
        return success(true);
    }

    @PutMapping("/update")
    @Operation(summary = "更新字典数据")
    @PreAuthorize("@spe.hasPermission('system:dict:update')")
    @OperateLog(operateName = "更新字典数据", operateType = UPDATE)
    public CommonResult<Boolean> updateDictData(@Valid @RequestBody DictDataUpdateReqVO dictDataUpdateReqVO) {
        dictDataService.updateDictData(dictDataUpdateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除字典数据")
    @PreAuthorize("@spe.hasPermission('system:dict:delete')")
    @OperateLog(operateName = "删除字典数据", operateType = DELETE)
    public CommonResult<Boolean> deleteDictDataById(@PathVariable("id") Long id) {
        dictDataService.deleteDictDataById(id);
        return success(true);
    }
}

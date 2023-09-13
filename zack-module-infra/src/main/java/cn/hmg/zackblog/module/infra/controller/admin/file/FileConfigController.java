package cn.hmg.zackblog.module.infra.controller.admin.file;

import cn.hmg.zackblog.framework.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.operatelog.core.annotation.OperateLog;
import cn.hmg.zackblog.module.infra.controller.admin.file.vo.config.*;
import cn.hmg.zackblog.module.infra.convert.file.config.FileConfigConvert;
import cn.hmg.zackblog.module.infra.service.file.IFileConfigService;
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
 * 文件配置（例如本地、阿里OSS、七牛云上传等） 前端控制器
 * </p>
 *
 * @author hmg
 * @since 2023-08-27
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "后台-文件管理-文件配置")
@RequestMapping("/admin/infra/file-config")
public class FileConfigController {
    private final IFileConfigService fileConfigService;

    @GetMapping("/page")
    @PreAuthorize("@spe.hasPermission('infra:file-config:list')")
    @Operation(summary = "文件配置分页列表")
    public CommonResult<PageResult<FileConfigPageRespVO>> page(@Valid FileConfigPageReqVO fileConfigPageReqVO) {
        return success(FileConfigConvert.INSTANCE.convert(fileConfigService.getPage(fileConfigPageReqVO)));
    }

    @PostMapping("/create")
    @PreAuthorize("@spe.hasPermission('infra:file-config:create')")
    @Operation(summary = "创建文件配置")
    @OperateLog(operateName = "创建文件配置", operateType = CREATE)
    public CommonResult<Boolean> createFileConfig(@Valid @RequestBody FileConfigCreateReqVO reqVO){
        fileConfigService.createFileConfig(reqVO);
        return success(true);
    }

    @PutMapping("/update")
    @PreAuthorize("@spe.hasPermission('infra:file-config:update')")
    @Operation(summary = "更新文件配置")
    @OperateLog(operateName = "更新文件配置", operateType = UPDATE)
    public CommonResult<Boolean> updateFileConfig(@Valid @RequestBody FileConfigUpdateReqVO reqVO) {
        fileConfigService.updateFileConfig(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("@spe.hasPermission('infra:file-config:delete')")
    @Operation(summary = "删除文件配置")
    @OperateLog(operateName = "删除文件配置", operateType = DELETE)
    public CommonResult<Boolean> deleteFileConfigById(@PathVariable("id") Long id){
        fileConfigService.deleteFileConfigById(id);
        return success(true);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("@spe.hasPermission('infra:file-config:query')")
    @Operation(summary = "根据id获取文件配置")
    @OperateLog(operateName = "根据id获取文件配置", operateType = QUERY)
    public CommonResult<FileConfigRespVO> getFileConfigById(@PathVariable("id") Long id) {
        return success(FileConfigConvert.INSTANCE.convert(fileConfigService.getFileConfigById(id)));
    }

    @PutMapping("/update-master/{id}")
    @PreAuthorize("@spe.hasPermission('infra:file-config:update-master')")
    @Operation(summary = "更新文件配置主配置")
    @OperateLog(operateName = "更新文件配置主配置", operateType = CREATE)
    public CommonResult<Boolean> updateFileConfigMaster(@PathVariable("id") Long id) {
        fileConfigService.updateFileConfigMaster(id);
        return success(true);
    }

    @GetMapping("/test-upload/{id}")
    @PreAuthorize("@spe.hasPermission('infra:file-config:test-upload')")
    @Operation(summary = "测试上传")
    @OperateLog(operateName = "测试上传", operateType = OTHER)
    public CommonResult<String> testUpload(@PathVariable("id") Long id) throws Exception {
        String url = fileConfigService.testUpload(id);
        return success(url);
    }
}

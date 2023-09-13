package cn.hmg.zackblog.module.infra.controller.admin.file;

import cn.hmg.zackblog.framework.common.exception.BusinessException;
import cn.hmg.zackblog.framework.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.framework.operatelog.core.annotation.OperateLog;
import cn.hmg.zackblog.module.infra.controller.admin.file.vo.file.FilePageReqVO;
import cn.hmg.zackblog.module.infra.controller.admin.file.vo.file.FilePageRespVO;
import cn.hmg.zackblog.module.infra.convert.file.FileConvert;
import cn.hmg.zackblog.module.infra.service.file.IFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static cn.hmg.zackblog.framework.common.pojo.CommonResult.success;
import static cn.hmg.zackblog.framework.operatelog.core.enums.OperateLogTypeEnum.*;
import static cn.hmg.zackblog.module.infra.enums.ErrorCodeEnum.FILE_CANNOT_EMPTY;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hmg
 * @since 2023-08-27
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "后台-文件管理-文件列表")
@RequestMapping("/admin/infra/file")
public class FileController {

    private final IFileService fileService;

    @GetMapping("/page")
    @PreAuthorize("@spe.hasPermission('infra:file:list')")
    @Operation(summary = "文件分页列表")
    @OperateLog(operateName = "文件分页列表", operateType = QUERY)
    public CommonResult<PageResult<FilePageRespVO>> page(@Valid FilePageReqVO reqVO) {
        return success(FileConvert.INSTANCE.convert(fileService.getPage(reqVO)));
    }

    @PostMapping("/upload")
    @PreAuthorize("@spe.hasPermission('infra:file:upload')")
    @Operation(summary = "文件上传")
    @OperateLog(operateName = "文件上传", operateType = CREATE)
    public CommonResult<String> uploadFile(@RequestParam(value = "file") MultipartFile multipartFile) throws Exception {
        if (multipartFile.isEmpty()) {
            throw new BusinessException(FILE_CANNOT_EMPTY.getCode(), FILE_CANNOT_EMPTY.getMessage());
        }

        return success(fileService.uploadFile(multipartFile.getInputStream(), multipartFile.getOriginalFilename()));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("@spe.hasPermission('infra:file:delete')")
    @Operation(summary = "删除文件")
    @OperateLog(operateName = "删除文件", operateType = DELETE)
    public CommonResult<Boolean> deleteFileById(@PathVariable("id") Long id) throws Exception {
        fileService.deleteFileById(id);
        return success(true);
    }
}

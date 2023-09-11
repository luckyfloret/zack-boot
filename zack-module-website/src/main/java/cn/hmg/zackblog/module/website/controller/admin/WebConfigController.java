package cn.hmg.zackblog.module.website.controller.admin;

import cn.hmg.zackblog.framework.common.exception.BusinessException;
import cn.hmg.zackblog.framework.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.operatelog.core.annotation.OperateLog;
import cn.hmg.zackblog.module.website.controller.admin.vo.webconfig.AuthorInfoVO;
import cn.hmg.zackblog.module.website.controller.admin.vo.webconfig.WebConfigUploadReqVO;
import cn.hmg.zackblog.module.website.controller.admin.vo.webconfig.WebsiteInfoVO;
import cn.hmg.zackblog.module.website.convert.admin.webconfig.WebConfigConvert;
import cn.hmg.zackblog.module.website.service.IWebConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static cn.hmg.zackblog.framework.common.pojo.CommonResult.success;
import static cn.hmg.zackblog.framework.operatelog.core.enums.OperateLogTypeEnum.QUERY;
import static cn.hmg.zackblog.framework.operatelog.core.enums.OperateLogTypeEnum.UPDATE;
import static cn.hmg.zackblog.module.website.enums.ErrorCodeEnum.WEB_CONFIG_FILE_CANNOT_EMPTY;

/**
 * <p>
 * 网站配置 前端控制器
 * </p>
 *
 * @author hmg
 * @since 2023-08-30
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "后台-网站配置")
@RequestMapping("/admin/website/web-config")
public class WebConfigController {
    private final IWebConfigService webConfigService;

    @GetMapping("/get-website-info")
    @PreAuthorize("@spe.hasPermission('website:web-config:query')")
    @Operation(summary = "获取网站信息")
    @OperateLog(operateName = "获取网站信息", operateType = QUERY)
    public CommonResult<WebsiteInfoVO> getWebsiteInfo() {
        return success(WebConfigConvert.INSTANCE.convert(webConfigService.getWebSiteInfo()));
    }

    @GetMapping("/get-author-info")
    @PreAuthorize("@spe.hasPermission('website:web-config:query')")
    @Operation(summary = "获取作者信息")
    @OperateLog(operateName = "获取作者信息", operateType = QUERY)
    public CommonResult<AuthorInfoVO> getAuthorInfo() {
        return success(WebConfigConvert.INSTANCE.convertToAuthorInfoVO(webConfigService.getAuthorInfo()));
    }

    @PutMapping("/update-website-info")
    @PreAuthorize("@spe.hasPermission('website:web-config:update')")
    @Operation(summary = "更新网站信息")
    @OperateLog(operateName = "更新网站信息", operateType = UPDATE)
    public CommonResult<Boolean> updateWebsiteInfo(@Valid @RequestBody WebsiteInfoVO websiteInfoVO) {
        webConfigService.updateWebsiteInfo(websiteInfoVO);
        return success(true);
    }

    @PutMapping("/update-author-info")
    @PreAuthorize("@spe.hasPermission('website:web-config:update')")
    @Operation(summary = "更新作者信息")
    @OperateLog(operateName = "更新作者信息", operateType = UPDATE)
    public CommonResult<Boolean> updateAuthorInfo(@Valid @RequestBody AuthorInfoVO authorInfoVO) {
        webConfigService.updateAuthorInfo(authorInfoVO);
        return success(true);
    }
}

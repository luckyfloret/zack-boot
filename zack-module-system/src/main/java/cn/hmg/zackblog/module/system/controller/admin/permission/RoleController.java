package cn.hmg.zackblog.module.system.controller.admin.permission;

import cn.hmg.zackblog.framework.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.role.*;
import cn.hmg.zackblog.module.system.convert.permission.RoleConvert;
import cn.hmg.zackblog.module.system.service.permission.IRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static cn.hmg.zackblog.framework.common.pojo.CommonResult.success;

/**
 * <p>
 * 角色管理 前端控制器
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Tag(name = "后台-角色管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/system/role")
public class RoleController {

    private final IRoleService roleService;

    @GetMapping("/page")
    @Operation(summary = "角色分页")
    @PreAuthorize("@spe.hasPermission('system:role:list')")
    public CommonResult<PageResult<RolePageRespVO>> page(@Valid RolePageReqVO rolePageReqVO){
        PageResult<RolePageRespVO> pageResult = roleService.getPage(rolePageReqVO);
        return success(pageResult);
    }

    @PostMapping("/create")
    @Operation(summary = "创建角色")
    @PreAuthorize("@spe.hasPermission('system:role:create')")
    public CommonResult<Boolean> createRole(@Valid @RequestBody RoleCreateReqVO roleCreateReqVO){
        roleService.createRole(roleCreateReqVO);
        return success(true);
    }

    @PutMapping("/update")
    @Operation(summary = "更新角色")
    @PreAuthorize("@spe.hasPermission('system:role:update')")
    public CommonResult<Boolean> updateRole(@Valid @RequestBody RoleUpdateReqVO roleUpdateReqVO){
        roleService.updateRole(roleUpdateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除角色")
    @PreAuthorize("@spe.hasPermission('system:role:delete')")
    public CommonResult<Boolean> deleteRoleById(@PathVariable("id") Long id){
        roleService.deleteRoleById(id);
        return success(true);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "根据id获取角色信息")
    @PreAuthorize("@spe.hasPermission('system:role:query')")
    public CommonResult<RoleRespVO> getById(@PathVariable("id") Long id){
        return success(RoleConvert.INSTANCE.convertRoleRespVO(roleService.getRoleById(id)));
    }

    @GetMapping("/export")
    @Operation(summary = "角色导出 - 待开发")
    @PreAuthorize("@spe.hasPermission('system:role:export')")
    public void export(RolePageReqVO rolePageReqVO){

    }

}

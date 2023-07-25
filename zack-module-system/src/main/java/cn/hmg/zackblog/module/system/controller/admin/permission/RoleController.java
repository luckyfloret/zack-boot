package cn.hmg.zackblog.module.system.controller.admin.permission;

import cn.hmg.zackblog.common.pojo.CommonResult;
import cn.hmg.zackblog.common.pojo.PageResult;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.role.RoleCreateReqVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.role.RolePageReqVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.role.RolePageRespVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.role.RoleUpdateReqVO;
import cn.hmg.zackblog.module.system.service.permission.IRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static cn.hmg.zackblog.common.pojo.CommonResult.success;

/**
 * <p>
 * 角色管理 前端控制器
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Tag(name = "角色管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/system/role")
public class RoleController {

    private final IRoleService roleService;

    @GetMapping("/page")
    @Operation(summary = "角色列表")
    @PreAuthorize("@spe.hasPermission('system:role:list')")
    public CommonResult<PageResult<RolePageRespVO>> page(@Valid RolePageReqVO rolePageReqVO){
        PageResult<RolePageRespVO> pageResult = roleService.getPage(rolePageReqVO);
        return success(pageResult);
    }

    @PostMapping("/create")
    @Operation(summary = "创建菜单")
    @PreAuthorize("@spe.hasPermission('system:role:create')")
    public CommonResult<Boolean> createRole(@Valid @RequestBody RoleCreateReqVO roleCreateReqVO){
        roleService.createRole(roleCreateReqVO);
        return success(true);
    }

    @PutMapping("/update")
    @Operation(summary = "更新菜单")
    @PreAuthorize("@spe.hasPermission('system:role:update')")
    public CommonResult<Boolean> updateRole(@Valid @RequestBody RoleUpdateReqVO roleUpdateReqVO){
        roleService.updateRole(roleUpdateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除菜单")
    @PreAuthorize("@spe.hasPermission('system:role:delete')")
    public CommonResult<Boolean> deleteRoleById(@PathVariable("id") Long id){
        roleService.deleteRoleById(id);
        return success(true);
    }

    @GetMapping("/export")
    @Operation(summary = "角色导出 - 待开发")
    @PreAuthorize("@spe.hasPermission('system:role:export')")
    public void export(RolePageReqVO rolePageReqVO){

    }

}

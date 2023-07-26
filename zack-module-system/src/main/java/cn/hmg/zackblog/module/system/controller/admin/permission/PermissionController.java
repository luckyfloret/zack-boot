package cn.hmg.zackblog.module.system.controller.admin.permission;

import cn.hmg.zackblog.common.pojo.CommonResult;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.permission.PermissionAssignUserRoleReqVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.permission.PermissionMenuAssignReqVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.permission.PermissionMenuListRespVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.permission.PermissionRoleListRespVO;
import cn.hmg.zackblog.module.system.convert.permission.PermissionConvert;
import cn.hmg.zackblog.module.system.entity.permission.Role;
import cn.hmg.zackblog.module.system.service.permission.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static cn.hmg.zackblog.common.pojo.CommonResult.success;
import static cn.hmg.zackblog.common.enums.CommonStatusEnum.*;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-18 16:29
 * @description: 权限控制器
 */
@Tag(name = "权限控制")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/system/permission")
public class PermissionController {

    private final PermissionService permissionService;


    @GetMapping("/get-role-permission/{roleId}")
    @Operation(summary = "获取角色权限资源")
    @PreAuthorize("@spe.hasPermission('system:permission:assign-role-menu')")
    public CommonResult<Set<Long>> getRolePermission(@PathVariable("roleId") Long roleId) {
        return success(permissionService.getRolePermissionByRoleIdFromCache(roleId));
    }

    @GetMapping("/list-menu")
    @Operation(summary = "菜单列表，只需要开启的，用于分配菜单权限时获取")
    @PreAuthorize("@spe.hasPermission('system:menu:list')")
    public CommonResult<List<PermissionMenuListRespVO>> getMenuList() {
        return success(permissionService.getMenuListByStatus(ENABLED.getStatusCode()));
    }

    @PostMapping("/assign-menu-permission")
    @Operation(summary = "分配菜单权限")
    @PreAuthorize("@spe.hasPermission('system:permission:assign-role-menu')")
    public CommonResult<Boolean> assignMenuPermission(@Valid @RequestBody PermissionMenuAssignReqVO reqVO) {
        permissionService.assignMenuPermission(reqVO);
        return success(true);
    }

    @GetMapping("/get-user-roles/{userId}")
    @Operation(summary = "获取用户对应的角色组")
    @PreAuthorize("@spe.hasPermission('system:permission:assign-user-role')")
    public CommonResult<Set<Long>> getUserRoles(@PathVariable("userId") Long userId) {
        return success(permissionService.getRoleIdsByUserIdFromCache(userId, ENABLED.getStatusCode()));
    }

    @PostMapping("/assign-user-role")
    @Operation(summary = "用户分配角色")
    @PreAuthorize("@spe.hasPermission('system:permission:assign-user-role')")
    public CommonResult<Boolean> assignUserRole(@Valid @RequestBody PermissionAssignUserRoleReqVO reqVO) {
        permissionService.assignUserRole(reqVO);
        return success(true);
    }

    @GetMapping("/list-role")
    @Operation(summary = "角色列表，用于分配角色时获取")
    @PreAuthorize("@spe.hasPermission('system:role:list')")
    public CommonResult<List<PermissionRoleListRespVO>> listRole(){
        List<Role> roleList = permissionService.listRoleByStatus(ENABLED.getStatusCode());
        return success(PermissionConvert.INSTANCE.convertPermissionRoleListRespVO(roleList));
    }
}

package cn.hmg.zackblog.module.system.controller.admin.auth;

import cn.hmg.zackblog.common.enums.CommonStatusEnum;
import cn.hmg.zackblog.common.enums.UserTypeEnum;
import cn.hmg.zackblog.common.pojo.CommonResult;
import cn.hmg.zackblog.common.utils.collections.CollectionUtils;
import cn.hmg.zackblog.framework.core.utils.SecurityUtils;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.AdminAuthLoginReqVO;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.AdminAuthLoginRespVO;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.AdminAuthMenuRespVO;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.AdminAuthPermissionRespVO;
import cn.hmg.zackblog.module.system.convert.auth.AdminAuthConvert;
import cn.hmg.zackblog.module.system.entity.permission.Menu;
import cn.hmg.zackblog.module.system.enums.MenuTypeEnum;
import cn.hmg.zackblog.module.system.service.auth.AuthService;
import cn.hmg.zackblog.module.system.service.permission.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-08 11:10
 * @description: 认证控制器、用于登录、登出
 */
@Tag(name = "用户认证")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admin/system/auth")
public class AdminAuthController {
    private final AuthService authService;

    private final PermissionService permissionService;

    @PostMapping("/login")
    @Operation(summary = "账号密码登录")
    public CommonResult<AdminAuthLoginRespVO> login(@RequestBody AdminAuthLoginReqVO adminAuthLoginReqVO){
        return CommonResult.success(authService.login(adminAuthLoginReqVO, UserTypeEnum.ADMIN_USER));
    }


    @PostMapping("refresh-token")
    @Operation(summary = "刷新令牌")
    public CommonResult<AdminAuthLoginRespVO> refreshToken(String refreshToken){
        AdminAuthLoginRespVO adminAuthLoginRespVO = authService.refreshToken(refreshToken);
        return CommonResult.success(adminAuthLoginRespVO);
    }

    @GetMapping("/get-permission")
    @Operation(summary = "获取权限信息")
    public CommonResult<AdminAuthPermissionRespVO> getPermission(){
        return CommonResult.success();
    }

    @GetMapping("/user-menu-nav")
    @Operation(summary = "登录用户的菜单导航")
    public CommonResult<List<Menu>> getUserMenuNav(){
        Set<Long> roleIds = permissionService.getRoleIdsByUserIdFromCache(SecurityUtils.getLoginUserId(), CommonStatusEnum.ENABLED.getStatusCode());
        Set<Long> menuIds = permissionService.getMenuIdsByRoleIdsFromCache(roleIds, CollectionUtils.asSet(MenuTypeEnum.DIR.getCode(),
                MenuTypeEnum.MENU.getCode()), CommonStatusEnum.ENABLED.getStatusCode());
        List<Menu> menuListFromCache = permissionService.getMenuListFromCache(menuIds);
        return CommonResult.success(menuListFromCache);
//        return CommonResult.success(AdminAuthConvert.INSTANCE.buildMenuTree(menuListFromCache));
    }
}

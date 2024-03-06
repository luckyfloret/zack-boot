package cn.hmg.zackblog.module.system.controller.admin.permission;

import cn.hmg.zackblog.framework.common.exception.BusinessException;
import cn.hmg.zackblog.framework.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.operatelog.core.annotation.OperateLog;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.menu.MenuCreateReqVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.menu.MenuListReqVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.menu.MenuRespVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.menu.MenuUpdateReqVO;
import cn.hmg.zackblog.module.system.convert.permission.MenuConvert;
import cn.hmg.zackblog.module.system.entity.permission.Menu;
import cn.hmg.zackblog.module.system.service.permission.IMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static cn.hmg.zackblog.framework.common.pojo.CommonResult.success;
import static cn.hmg.zackblog.module.system.enums.ErrorCodeEnum.MENU_NOT_EXISTS;
import static cn.hmg.zackblog.framework.operatelog.core.enums.OperateLogTypeEnum.*;
/**
 * <p>
 * 后台系统菜单 前端控制器
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Tag(name = "后台-菜单管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/system/menu")
public class MenuController {

    private final IMenuService menuService;

    @PreAuthorize("@spe.hasPermission('system:menu:list')")
    @GetMapping("/list")
    @Operation(summary = "菜单列表")
    public CommonResult<List<MenuRespVO>> list(MenuListReqVO menuListReqVO){
        List<Menu> menuList = menuService.getMenuList(menuListReqVO);
        return success(MenuConvert.INSTANCE.buildMenuTree(menuList));
    }

    @PreAuthorize("@spe.hasPermission('system:menu:create')")
    @PostMapping("/create")
    @Operation(summary = "创建菜单")
    @OperateLog(operateName = "创建菜单", operateType = CREATE)
    public CommonResult<Boolean> createMenu(@Valid @RequestBody MenuCreateReqVO menuCreateReqVO){
        menuService.createMenu(menuCreateReqVO);
        return success(true);
    }


    @PreAuthorize("@spe.hasPermission('system:menu:update')")
    @PutMapping("/update")
    @Operation(summary = "更新菜单")
    @OperateLog(operateName = "更新菜单", operateType = UPDATE)
    public CommonResult<Boolean> updateMenu(@Valid @RequestBody MenuUpdateReqVO menuUpdateReqVO){
        menuService.updateMenu(menuUpdateReqVO);
        return success(true);
    }

    @PreAuthorize("@spe.hasPermission('system:menu:delete')")
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除菜单")
    @OperateLog(operateName = "删除菜单", operateType = DELETE)
    public CommonResult<Boolean> deleteMenuById(@PathVariable("id") Long id){
        menuService.deleteMenuById(id);
        return success(true);
    }


    @OperateLog(operateType = QUERY, operateName = "根据id获取菜单信息")
    @PreAuthorize("@spe.hasPermission('system:menu:query')")
    @GetMapping("/get/{id}")
    @Operation(summary = "根据id获取菜单信息")
    public CommonResult<Menu> getMenuById(@PathVariable("id") Long id){
        return success(Optional.ofNullable(menuService.getMenuByIdFromCache(id)).orElseThrow(() -> new BusinessException(MENU_NOT_EXISTS.getCode(), MENU_NOT_EXISTS.getMessage())));
    }
}

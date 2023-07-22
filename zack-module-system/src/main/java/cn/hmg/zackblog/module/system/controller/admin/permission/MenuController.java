package cn.hmg.zackblog.module.system.controller.admin.permission;

import cn.hmg.zackblog.common.pojo.CommonResult;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.menu.MenuCreateReqVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.menu.MenuListReqVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.menu.MenuRespVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.menu.MenuUpdateReqVO;
import cn.hmg.zackblog.module.system.service.permission.IMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static cn.hmg.zackblog.common.pojo.CommonResult.success;

/**
 * <p>
 * 后台系统菜单 前端控制器
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Tag(name = "菜单管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/system/menu")
public class MenuController {

    private final IMenuService menuService;

    @GetMapping("/list")
    @Operation(summary = "菜单列表")
    public CommonResult<MenuRespVO> list(MenuListReqVO menuListReqVO){

        return success();
    }

    @PostMapping("/create")
    @Operation(summary = "创建菜单")
    public CommonResult<Boolean> createMenu(MenuCreateReqVO menuCreateReqVO){

        return success();
    }


    @PutMapping("/update")
    @Operation(summary = "更新菜单")
    public CommonResult<Boolean> updateMenu(MenuUpdateReqVO menuUpdateReqVO){
        return success();
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除菜单")
    public CommonResult<Boolean> deleteMenuById(@PathVariable("id") Long id){

        return success();
    }

}

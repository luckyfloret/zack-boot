package cn.hmg.zackblog.module.system.controller.admin.permission;

import cn.hmg.zackblog.common.pojo.CommonResult;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.MenuListReqVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.MenuRespVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/admin/system/menu")
public class MenuController {

    @GetMapping("/list")
    @Operation(summary = "菜单列表")
    public CommonResult<MenuRespVO> list(MenuListReqVO menuListReqVO){

        return CommonResult.success();
    }


}

package cn.hmg.zackblog.module.system.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户管理 前端控制器
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@RestController
@Api("用户管理模块")
@RequestMapping("/users")
public class UserController {
    @ApiOperation("测试方法")
    @GetMapping("test")
    public String test(){
        return "test...";
    }
}

package cn.hmg.zackblog.module.system.controller.admin.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@Tag(name = "用户管理模块")
@RequestMapping("/users")
public class UserController {
    @Operation(description = "测试方法")
    @GetMapping("test")
    public String test(){
        log.info("进入test方法。。。");
        log.debug("进入test方法。。。");
        return "test11...";
    }
}

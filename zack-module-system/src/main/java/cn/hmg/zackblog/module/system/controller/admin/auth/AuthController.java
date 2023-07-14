package cn.hmg.zackblog.module.system.controller.admin.auth;

import cn.hmg.zackblog.common.pojo.CommonResult;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.LoginReqVO;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.LoginRespVO;
import cn.hmg.zackblog.module.system.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-08 11:10
 * @description: 认证控制器、用于登录、登出
 */
@Tag(name = "用户认证")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(description = "账号密码登录")
    public CommonResult<LoginRespVO> login(@RequestBody LoginReqVO loginReqVO){
        return CommonResult.success(authService.login(loginReqVO));
    }
}

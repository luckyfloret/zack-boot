package cn.hmg.zackblog.module.system.controller.admin.auth;

import cn.hmg.zackblog.common.enums.UserTypeEnum;
import cn.hmg.zackblog.common.pojo.CommonResult;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.AdminAuthLoginReqVO;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.AdminAuthLoginRespVO;
import cn.hmg.zackblog.module.system.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

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
}

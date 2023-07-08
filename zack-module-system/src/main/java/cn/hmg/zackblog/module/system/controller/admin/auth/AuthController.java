package cn.hmg.zackblog.module.system.controller.admin.auth;

import cn.hmg.zackblog.common.pojo.CommonResult;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.LoginReqVO;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.LoginRespVO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-08 11:10
 * @description: 认证控制器、用于登录、登出
 */
@Api(tags = "认证控制器")
@RestController
public class AuthController {
    @PostMapping("/login")
    public CommonResult<LoginRespVO> login(@RequestBody LoginReqVO loginReqVO){
        return CommonResult.success();
    }
}

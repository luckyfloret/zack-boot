package cn.hmg.zackblog.module.system.controller.admin.user;

import cn.hmg.zackblog.framework.common.pojo.CommonResult;
import cn.hmg.zackblog.module.system.controller.admin.user.vo.UserUpdatePasswordReqVO;
import cn.hmg.zackblog.module.system.service.user.IUserService;
import cn.hmg.zackblog.module.system.service.user.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static cn.hmg.zackblog.framework.common.pojo.CommonResult.success;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-26 19:54
 * @description: 用户中心 or 个人中心
 */
@Tag(name = "后台-用户中心 or 个人中心")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/system/user-center")
public class UserCenterController {

    private final IUserService userService;

    @PutMapping("/update-password")
    @Operation(summary = "更新用户密码")
    @PreAuthorize("@spe.hasPermission('system:user:update-user-password')")
    public CommonResult<Boolean> updateUserPassword(@Valid @RequestBody UserUpdatePasswordReqVO userUpdatePasswordReqVO) {
        userService.updateUserPassword(userUpdatePasswordReqVO);
        return success(true);
    }
}

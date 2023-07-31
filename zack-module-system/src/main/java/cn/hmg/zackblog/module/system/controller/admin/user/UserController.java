package cn.hmg.zackblog.module.system.controller.admin.user;

import cn.hmg.zackblog.framework.common.exception.ServiceException;
import cn.hmg.zackblog.framework.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.common.pojo.PageResult;
import cn.hmg.zackblog.module.system.controller.admin.user.vo.*;
import cn.hmg.zackblog.module.system.convert.user.UserConvert;
import cn.hmg.zackblog.module.system.entity.user.User;
import cn.hmg.zackblog.module.system.service.user.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static cn.hmg.zackblog.framework.common.pojo.CommonResult.success;
import static cn.hmg.zackblog.module.system.enums.ErrorCodeEnum.USER_NOT_EXISTS;

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
@RequiredArgsConstructor
@Tag(name = "用户管理")
@RequestMapping("/admin/system/user")
public class UserController {

    private final IUserService userService;

    @GetMapping("/page")
    @Operation(summary = "用户分页列表")
    @PreAuthorize("@spe.hasPermission('system:user:list')")
    public CommonResult<PageResult<UserPageRespVO>> page(@Valid UserPageReqVO userPageReqVO) {
        PageResult<UserPageRespVO> pageResult = userService.getPage(userPageReqVO);
        return success(pageResult);
    }

    @PostMapping("/create")
    @Operation(summary = "创建用户")
    @PreAuthorize("@spe.hasPermission('system:user:create')")
    public CommonResult<Boolean> createUser(@Valid @RequestBody UserCreateReqVO userCreateReqVO) {
        userService.createUser(userCreateReqVO);
        return success(true);
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户")
    @PreAuthorize("@spe.hasPermission('system:user:update')")
    public CommonResult<Boolean> updateUser(@Valid @RequestBody UserUpdateReqVO userUpdateReqVO) {
        userService.updateUser(userUpdateReqVO);
        return success(true);
    }


    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除用户")
    @PreAuthorize("@spe.hasPermission('system:user:delete')")
    public CommonResult<Boolean> deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return success();
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "根据id获取用户信息")
    @PreAuthorize("@spe.hasPermission('system:user:query')")
    public CommonResult<UserRespVO> getById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new ServiceException(USER_NOT_EXISTS.getCode(), USER_NOT_EXISTS.getMessage()));
        return success(UserConvert.INSTANCE.convert(user));
    }

    @PutMapping("/reset-password")
    @Operation(summary = "重置密码")
    @PreAuthorize("@spe.hasPermission('system:user:reset-password')")
    public CommonResult<Boolean> resetPassword(@Valid @RequestBody UserResetPasswordReqVO userResetPasswordReqVO) {
        userService.resetPassword(userResetPasswordReqVO);
        return success(true);
    }

    @GetMapping("/export")
    @Operation(summary = "导出用户信息 - 待开发")
    @PreAuthorize("@spe.hasPermission('sysetm:user:export')")
    public void exportExcel() {
    }

    @GetMapping("/import")
    @Operation(summary = "导入用户信息 - 待开发")
    @PreAuthorize("@spe.hasPermission('sysetm:user:import')")
    public CommonResult<?> importExcel() {

        return success();
    }


}

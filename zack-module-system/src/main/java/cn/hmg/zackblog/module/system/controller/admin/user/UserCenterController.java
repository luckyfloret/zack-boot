package cn.hmg.zackblog.module.system.controller.admin.user;

import cn.hmg.zackblog.framework.common.exception.BusinessException;
import cn.hmg.zackblog.framework.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.security.core.utils.SecurityUtils;
import cn.hmg.zackblog.module.system.controller.admin.user.vo.UserUpdatePasswordReqVO;
import cn.hmg.zackblog.module.system.controller.admin.user.vo.center.UserCenterRespVO;
import cn.hmg.zackblog.module.system.controller.admin.user.vo.center.UserCenterUpdateReqVO;
import cn.hmg.zackblog.module.system.convert.permission.RoleConvert;
import cn.hmg.zackblog.module.system.convert.user.UserConvert;
import cn.hmg.zackblog.module.system.entity.permission.Role;
import cn.hmg.zackblog.module.system.entity.user.User;
import cn.hmg.zackblog.module.system.enums.ErrorCodeEnum;
import cn.hmg.zackblog.module.system.service.permission.IRoleService;
import cn.hmg.zackblog.module.system.service.permission.PermissionService;
import cn.hmg.zackblog.module.system.service.user.IUserService;
import cn.hmg.zackblog.module.system.service.user.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Set;

import static cn.hmg.zackblog.framework.common.pojo.CommonResult.success;
import static cn.hmg.zackblog.framework.security.core.utils.SecurityUtils.getLoginUserId;
import static cn.hmg.zackblog.module.system.enums.ErrorCodeEnum.USER_NOT_EXISTS;

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

    private final PermissionService permissionService;

    private final IRoleService roleService;

    @PutMapping("/update-password")
    @Operation(summary = "更新用户密码")
    public CommonResult<Boolean> updateUserPassword(@Valid @RequestBody UserUpdatePasswordReqVO userUpdatePasswordReqVO) {
        userService.updateUserPassword(getLoginUserId(), userUpdatePasswordReqVO);
        return success(Boolean.TRUE);
    }

    @GetMapping("/getUserInfo")
    @Operation(summary = "获取用户信息")
    public CommonResult<UserCenterRespVO> getUserInfo() {
        //获取用户基本信息
        User user = userService.getUserById(getLoginUserId()).orElseThrow(() -> new BusinessException(USER_NOT_EXISTS.getCode(), USER_NOT_EXISTS.getMessage()));
        UserCenterRespVO userCenterRespVO = UserConvert.INSTANCE.convertUserCenterRespVO(user);

        //获取角色信息
        Set<Long> roleIds = permissionService.getRoleIdsByUserId(getLoginUserId());
        List<Role> roleList = roleService.getRoleListFromCacheByIds(roleIds);
        //组装信息
        userCenterRespVO.setRoles(RoleConvert.INSTANCE.convert(roleList));
        return success(userCenterRespVO);
    }

    @PutMapping("/update-personal-info")
    @Operation(summary = "修改用户个人信息")
    public CommonResult<Boolean> updateUserPersonalInfo(@Valid @RequestBody UserCenterUpdateReqVO userCenterUpdateReqVO) {
        userService.updateUserPersonalInfo(getLoginUserId(), userCenterUpdateReqVO);
        return success(Boolean.TRUE);
    }
}

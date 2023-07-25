package cn.hmg.zackblog.framework.core.permission;

import cn.hmg.zackblog.framework.core.service.SecurityPermissionService;
import lombok.RequiredArgsConstructor;

import static cn.hmg.zackblog.framework.core.utils.SecurityUtils.getLoginUserId;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-24 14:54
 * @description: SecurityPermissionExpression实现类
 */
@RequiredArgsConstructor
public class SecurityPermissionExpressionImpl implements SecurityPermissionExpression {
    private final SecurityPermissionService securityPermissionService;

    @Override
    public boolean hasPermission(String permissions) {
        return hasAnyPermission(permissions);
    }

    @Override
    public boolean hasAnyPermission(String... permissions) {
        return securityPermissionService.hasAnyPermission(getLoginUserId(), permissions);
    }
}

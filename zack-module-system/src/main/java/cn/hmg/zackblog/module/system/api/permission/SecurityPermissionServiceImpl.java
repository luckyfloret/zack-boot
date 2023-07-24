package cn.hmg.zackblog.module.system.api.permission;

import cn.hmg.zackblog.framework.core.service.SecurityPermissionService;
import cn.hmg.zackblog.module.system.service.permission.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-24 14:54
 * @description: SecurityPermissionExpression实现类
 */
@Service
public class SecurityPermissionServiceImpl implements SecurityPermissionService {

    @Resource
    private PermissionService permissionService;

    @Override
    public boolean hasAnyPermission(Long userId, String... permissions) {
        return permissionService.hasAnyPermission(userId, permissions);
    }
}

package cn.hmg.zackblog.framework.security.core.service;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-24 15:01
 * @description: Security权限校验接口，提供给业务模块实现
 */
public interface SecurityPermissionService {
    /**
     * 校验权限，任意一个匹配即可
     * @param userId 用户id
     * @param permission 权限编码
     * @return true or false
     */
    boolean hasAnyPermission(Long userId, String ... permission);
}

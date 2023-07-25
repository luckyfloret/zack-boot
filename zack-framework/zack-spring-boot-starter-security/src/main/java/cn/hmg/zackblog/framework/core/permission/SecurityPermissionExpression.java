package cn.hmg.zackblog.framework.core.permission;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-24 14:21
 * @description: Customize Spring Security权限表达式接口
 */
public interface SecurityPermissionExpression {

    /**
     * 根据权限编码校验权限
     * @param permissions 权限编码
     * @return true or false
     */
    boolean hasPermission(String permissions);

    /**
     * 根据权限编码校验权限，且任意一个匹配即可
     * @param permissions 权限编码
     * @return true or false
     */
    boolean hasAnyPermission(String... permissions);
}

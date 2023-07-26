package cn.hmg.zackblog.module.system.service.permission;

import cn.hmg.zackblog.module.system.controller.admin.auth.vo.AdminAuthPermissionRespVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.permission.PermissionAssignUserRoleReqVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.permission.PermissionMenuAssignReqVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.permission.PermissionMenuListRespVO;
import cn.hmg.zackblog.module.system.entity.permission.Menu;
import cn.hmg.zackblog.module.system.entity.permission.Role;

import java.util.List;
import java.util.Set;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-18 16:28
 * @description: 权限服务
 */
public interface PermissionService {

    /**
     * 初始化本地缓存
     */
    void initLocalCache();


    /**
     * 根据用户id从缓存中获取roleIds
     *
     * @param userId     用户id
     * @param roleStatus 角色状态
     * @return roleIds
     */
    Set<Long> getRoleIdsByUserIdFromCache(Long userId, Integer roleStatus);

    /**
     * 根据roleIds从缓存中获取menuIds
     *
     * @param roleIds    角色ids
     * @param menuStatus 菜单状态
     * @return menuIds
     */
    Set<Long> getMenuIdsByRoleIdsFromCache(Set<Long> roleIds, Set<Integer> menuTypes, Integer menuStatus);

    /**
     * 根据menuIds从缓存中获取菜单列表
     *
     * @param menuIds 菜单id集合
     * @return 菜单列表
     */
    List<Menu> getMenuListByIdsFromCache(Set<Long> menuIds);


    /**
     * 获取用户权限信息
     *
     * @param menuList 菜单列表
     * @return AdminAuthPermissionRespVO
     */
    AdminAuthPermissionRespVO getPermissionInfo(List<Menu> menuList);

    /**
     * 校验权限
     *
     * @param userId 登录用户id
     * @param permissions 权限编码
     * @return true or false
     */
    boolean hasAnyPermission(Long userId, String... permissions);

    void deleteRoleAssociation(Long roleId);

    List<PermissionMenuListRespVO> getMenuListByStatus(Integer status);

    Set<Long> getRolePermissionByRoleIdFromCache(Long roleId);

    void assignMenuPermission(PermissionMenuAssignReqVO reqVO);

    void deleteUserRoleAssociation(Long userId);

    void assignUserRole(PermissionAssignUserRoleReqVO reqVO);

    List<Role> listRoleByStatus(Integer status);
}

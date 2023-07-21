package cn.hmg.zackblog.module.system.service.permission;

import cn.hmg.zackblog.module.system.controller.admin.permission.vo.MenuRespVO;
import cn.hmg.zackblog.module.system.entity.permission.Menu;

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

    List<Menu> getMenuListFromCache(Set<Long> menuIds);
}

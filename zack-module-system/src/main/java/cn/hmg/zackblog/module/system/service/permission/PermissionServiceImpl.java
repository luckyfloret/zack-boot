package cn.hmg.zackblog.module.system.service.permission;

import cn.hmg.zackblog.common.enums.CommonStatusEnum;
import cn.hmg.zackblog.common.exception.ServiceException;
import cn.hmg.zackblog.common.utils.collections.CollectionUtils;
import cn.hmg.zackblog.common.utils.collections.MapUtils;
import cn.hmg.zackblog.framework.core.utils.SecurityUtils;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.AdminAuthPermissionRespVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.menu.MenuListReqVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.permission.PermissionMenuListRespVO;
import cn.hmg.zackblog.module.system.convert.auth.AdminAuthConvert;
import cn.hmg.zackblog.module.system.convert.permission.PermissionConvert;
import cn.hmg.zackblog.module.system.entity.permission.Menu;
import cn.hmg.zackblog.module.system.entity.permission.Role;
import cn.hmg.zackblog.module.system.entity.permission.RoleMenu;
import cn.hmg.zackblog.module.system.entity.permission.UserRole;
import cn.hmg.zackblog.module.system.entity.user.User;
import cn.hmg.zackblog.module.system.enums.ErrorCodeEnum;
import cn.hmg.zackblog.module.system.mapper.permission.RoleMenuMapper;
import cn.hmg.zackblog.module.system.mapper.permission.UserRoleMapper;
import cn.hmg.zackblog.module.system.service.user.IUserService;
import cn.hutool.core.util.ObjUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-18 16:29
 * @description: 权限Service
 */
@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService {

    /**
     * 角色和菜单关联表的缓存，采用set是为了去重
     * 使用volatile关键字修饰是为了在高并发场景下保证变量的可见性，有更新立即从主存中读取
     */
    @Getter
    private volatile Map<Long, Set<Long>> roleMenuCache;

    /**
     * 用户和角色关联表的缓存，采用set是为了去重
     * 使用volatile关键字修饰是为了在高并发场景下保证变量的可见性，有更新立即从主存中读取
     */
    @Getter
    private volatile Map<Long, Set<Long>> userRoleCache;


    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private IRoleService roleService;

    @Resource
    private IMenuService menuService;

    @Resource
    private IUserService userService;

    /**
     * 初始化角色菜单缓存
     */
    private void initRoleMenuCache() {
        //查询数据
        List<RoleMenu> roleMenus = roleMenuMapper.selectList();
        log.info("[initRoleMenuCache] => 初始化角色菜单缓存，数量为：{}", roleMenus.size());
        //构建缓存
        roleMenuCache = CollectionUtils.convertMapByGrouping(roleMenus, RoleMenu::getRoleId, RoleMenu::getMenuId, Collectors.toSet());
    }

    /**
     * 初始化用户角色缓存
     */
    private void initUserRoleCache() {
        //查询数据
        List<UserRole> userRoles =
                userRoleMapper.selectList();
        log.info("[initUserRoleCache] => 初始化用户角色缓存，数量为：{}", userRoles.size());

        //构建缓存
        userRoleCache = CollectionUtils.convertMapByGrouping(userRoles, UserRole::getUserId, UserRole::getRoleId, Collectors.toSet());
    }


    @PostConstruct
    @Override
    public void initLocalCache() {
        initRoleMenuCache();
        initUserRoleCache();
    }

    @Override
    public Set<Long> getRoleIdsByUserIdFromCache(Long userId, Integer roleStatus) {
        //从缓存中获取roleIds
        Set<Long> roleIds = userRoleCache.get(userId);

        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptySet();
        }

        //状态过滤
        roleIds.removeIf(roleId -> {
            Role role = roleService.getRoleByIdFromCache(roleId);
            return role == null || ObjUtil.notEqual(roleStatus, role.getStatus());
        });
        return roleIds;
    }

    @Override
    public Set<Long> getMenuIdsByRoleIdsFromCache(Set<Long> roleIds, Set<Integer> menuTypes, Integer menuStatus) {
        //任意一个参数为空直接返回空菜单id
        if (CollectionUtils.isAnyEmpty(roleIds, menuTypes)) {
            return Collections.emptySet();
        }

        //获取到所有菜单id
        Set<Long> menuIds = MapUtils.mapConvertSet(roleMenuCache, roleIds);

        //过滤状态与菜单类型
        menuIds.removeIf(menuId -> {
            Menu menu = menuService.getMenuByIdFromCache(menuId);
            return menu == null || !menuTypes.contains(menu.getType()) || ObjUtil.notEqual(menuStatus, menu.getStatus());
        });
        return menuIds;
    }

    @Override
    public List<Menu> getMenuListByIdsFromCache(Set<Long> menuIds) {
        if (CollectionUtils.isEmpty(menuIds)) {
            return Collections.emptyList();
        }

        return menuService.getMenuListByIdsFromCache(menuIds);
    }

    @Override
    public AdminAuthPermissionRespVO getPermissionInfo(List<Menu> menuList) {
        //获取用户信息
        User user = userService.getUserById(SecurityUtils.getLoginUserId()).orElseThrow(() -> new ServiceException(ErrorCodeEnum.USER_NOT_EXISTS.getCode(), ErrorCodeEnum.USER_USERNAME_EXISTS.getMessage()));
        //构建权限信息
        Set<String> permissions = menuList.stream().map(Menu::getPermission).collect(Collectors.toSet());
        return AdminAuthConvert.INSTANCE.convertAdminAuthPermissionRespVO(user, permissions);
    }

    @Override
    public boolean hasAnyPermission(Long userId, String... permissions) {
        //根据用户id获取角色
        Set<Long> roleIds = getRoleIdsByUserIdFromCache(userId, CommonStatusEnum.ENABLED.getStatusCode());
        //角色为空说明没有权限
        if (CollectionUtils.isEmpty(roleIds)) {
            return false;
        }

        //根据角色获取对应的权限信息
        Set<Long> menuIds = MapUtils.mapConvertSet(roleMenuCache, roleIds);
        //构建权限编码
        Set<String> permissionsFromCache = getMenuListByIdsFromCache(menuIds).stream().map(Menu::getPermission).collect(Collectors.toSet());
        //当前拥有的角色是否拥有此权限
        return permissionsFromCache.containsAll(Arrays.asList(permissions));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRoleAssociation(Long roleId) {
        //删除UserRole
        userRoleMapper.deleteByRoleId(roleId);

        //删除RoleMenu
        roleMenuMapper.deleteByRoleId(roleId);

        //TODO 通知MQ发送刷新缓存消息
    }

    @Override
    public List<PermissionMenuListRespVO> getMenuListByStatus(Integer status) {
        return PermissionConvert.INSTANCE.convert(menuService.getMenuListByStatus(status));
    }

    @Override
    public Set<Long> getRolePermissionByRoleIdFromCache(Long roleId) {
        return roleMenuCache.get(roleId);
    }

}

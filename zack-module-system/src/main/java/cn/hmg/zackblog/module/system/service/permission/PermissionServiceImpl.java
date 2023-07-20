package cn.hmg.zackblog.module.system.service.permission;

import cn.hmg.zackblog.common.utils.collections.CollectionUtils;
import cn.hmg.zackblog.module.system.entity.permission.RoleMenu;
import cn.hmg.zackblog.module.system.entity.permission.UserRole;
import cn.hmg.zackblog.module.system.mapper.permission.RoleMenuMapper;
import cn.hmg.zackblog.module.system.mapper.permission.UserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-18 16:29
 * @description: 权限Service
 */
@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService{

    /**
     * 角色和菜单关联表的缓存，采用set是为了去重
     *
     * 使用volatile关键字修饰是为了在高并发场景下保证变量的可见性，有更新立即从主存中读取
     */
    private volatile Map<Long, Set<Long>> roleMenuCache = new HashMap<>();

    /**
     * 用户和角色关联表的缓存，采用set是为了去重
     *
     * 使用volatile关键字修饰是为了在高并发场景下保证变量的可见性，有更新立即从主存中读取
     */
    private volatile Map<Long, Set<Long>> userRoleCache = new HashMap<>();


    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private UserRoleMapper userRoleMapper;


    private void initRoleMenuCache(){
        //查询数据
        List<RoleMenu> roleMenus = roleMenuMapper.selectList();
        log.info("[initRoleMenuCache] => 初始化角色菜单缓存，数量为：{}", roleMenus.size());
        //构建缓存
        roleMenuCache = CollectionUtils.convertMap(roleMenus, RoleMenu::getRoleId, RoleMenu::getMenuId, Collectors.toSet());
        log.info("初始化角色菜单缓存 => {}", roleMenuCache.get(1L));
    }

    private void initUserRoleCache(){
        //查询数据
        List<UserRole> userRoles =
                userRoleMapper.selectList();
        log.info("[initUserRoleCache] => 初始化用户角色缓存，数量为：{}", userRoles.size());

        //构建缓存
        userRoleCache = CollectionUtils.convertMap(userRoles, UserRole::getUserId, UserRole::getRoleId, Collectors.toSet());

        log.info("初始化用户角色缓存 => {}", userRoleCache.get(1L));
    }


    @PostConstruct
    @Override
    public void initLocalCache() {
        initRoleMenuCache();
        initUserRoleCache();
    }


}

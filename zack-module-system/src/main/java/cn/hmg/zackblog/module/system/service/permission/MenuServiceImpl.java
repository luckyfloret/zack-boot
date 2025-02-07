package cn.hmg.zackblog.module.system.service.permission;

import cn.hmg.zackblog.framework.common.exception.BusinessException;
import cn.hmg.zackblog.framework.common.utils.collections.CollectionUtils;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.menu.MenuCreateReqVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.menu.MenuListReqVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.menu.MenuUpdateReqVO;
import cn.hmg.zackblog.module.system.convert.permission.MenuConvert;
import cn.hmg.zackblog.module.system.entity.permission.Menu;
import cn.hmg.zackblog.module.system.entity.permission.Role;
import cn.hmg.zackblog.module.system.entity.permission.RoleMenu;
import cn.hmg.zackblog.module.system.enums.MenuTypeEnum;
import cn.hmg.zackblog.module.system.enums.RoleCodeEnum;
import cn.hmg.zackblog.module.system.mapper.permission.MenuMapper;
import cn.hmg.zackblog.module.system.mapper.permission.RoleMenuMapper;
import cn.hmg.zackblog.module.system.mq.producer.menu.MenuProducer;
import cn.hmg.zackblog.module.system.mq.producer.permission.PermissionProducer;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static cn.hmg.zackblog.module.system.entity.permission.Menu.ROOT;
import static cn.hmg.zackblog.module.system.enums.ErrorCodeEnum.*;

/**
 * <p>
 * 后台系统菜单 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Slf4j
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    /**
     * 菜单信息的缓存
     * 使用volatile关键字修饰是为了在高并发场景下保证变量的可见性，有更新立即从主存中读取
     */
    private volatile Map<Long, Menu> menuCache;

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IRoleService roleService;

    @Resource
    private MenuProducer menuProducer;

    @Resource
    private PermissionProducer permissionProducer;

    private void initMenuCache() {
        List<Menu> menus = menuMapper.selectList();
        log.info("[initMenuCache] => 初始化菜单信息缓存， 数量为：{}", menus.size());
        menuCache = CollectionUtils.convertMap(menus, Menu::getId);
    }

    @PostConstruct
    @Override
    public void initLocalCache() {
        initMenuCache();
    }

    @Override
    public Menu getMenuByIdFromCache(Long menuId) {
        return menuCache.get(menuId);
    }

    @Override
    public List<Menu> getMenuListByIdsFromCache(Set<Long> menuIds) {
        return menuCache.values().stream().filter(menu -> menuIds.contains(menu.getId())).collect(Collectors.toList());
    }

    @Override
    public List<Menu> getMenuList(MenuListReqVO menuListReqVO) {
        return menuMapper.selectList(menuListReqVO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createMenu(MenuCreateReqVO menuCreateReqVO) {
        Long parentId = menuCreateReqVO.getParentId();
        //校验父菜单
        verifyParentMenu(parentId, null);
        //校验当前添加的菜单
        verifyMenu(parentId, menuCreateReqVO.getName(), menuCreateReqVO.getType(), null);

        //对于按钮的属性设置
        Menu menu = MenuConvert.INSTANCE.convertMenu(menuCreateReqVO);
        setButtonProperties(menu);

        //插入菜单信息
        menuMapper.insert(menu);
        //新增菜单更新到超管权限菜单里
        Role superAdminRole = roleService.getSuperAdminRole(RoleCodeEnum.SUPER_ADMIN.getCode());
        roleMenuMapper.insert(new RoleMenu(null, superAdminRole.getId(), menu.getId()));
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            //事务提交后执行
            @Override
            public void afterCommit() {
                //通知MQ刷新缓存
                menuProducer.syncSendMenuRefreshCacheMessage();
                permissionProducer.syncSendPermissionRefreshCacheMessage();
            }
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateMenu(MenuUpdateReqVO menuUpdateReqVO) {
        //校验菜单是否存在
        verifyMenuIsExists(menuUpdateReqVO.getId());

        Long parentId = menuUpdateReqVO.getParentId();
        Long menuId = menuUpdateReqVO.getId();
        //校验父菜单
        verifyParentMenu(parentId, menuId);
        //校验当前更新的菜单
        verifyMenu(parentId, menuUpdateReqVO.getName(), menuUpdateReqVO.getType(), menuId);

        //按钮属性设置
        Menu menu = MenuConvert.INSTANCE.convertMenu(menuUpdateReqVO);
        setButtonProperties(menu);

        //更新菜单信息
        menuMapper.updateById(menu);
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            //事务提交后执行
            @Override
            public void afterCommit() {
                //通知MQ刷新缓存
                menuProducer.syncSendMenuRefreshCacheMessage();
            }
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteMenuById(Long menuId) {
        //校验菜单是否存在
        verifyMenuIsExists(menuId);

        //校验是否有子菜单
        if (menuMapper.selectCountByParentId(menuId) > 0) {
            throw new BusinessException(MENU_EXISTS_CHILD.getCode(), MENU_EXISTS_CHILD.getMessage());
        }

        //删除roleMenu
        roleMenuMapper.deleteByMenuId(menuId);

        //删除菜单
        menuMapper.deleteById(menuId);
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            //事务提交后执行
            @Override
            public void afterCommit() {
                //通知MQ刷新缓存
                menuProducer.syncSendMenuRefreshCacheMessage();
            }
        });

    }

    @Override
    public List<Menu> getMenuListByStatus(Integer status) {
        return menuMapper.selectList();
    }


    /**
     * 校验菜单是否存在
     * @param menuId 菜单id
     */
    private void verifyMenuIsExists(Long menuId){
        Menu menu = menuMapper.selectOne(menuId);
        if (Objects.isNull(menu)) {
            throw new BusinessException(MENU_NOT_EXISTS.getCode(), MENU_NOT_EXISTS.getMessage());
        }
    }


    /**
     * 如果菜单类型是按钮，那么Component、Icon、Path这些属性是不需要设置的
     *
     * @param menu 菜单信息
     */
    private void setButtonProperties(Menu menu) {
        if (MenuTypeEnum.BUTTON.getCode().equals(menu.getType())) {
            menu.setComponent("");
            menu.setIcon("");
            menu.setPath("");
        }
    }


    /**
     * 校验create or update的菜单信息
     *
     * @param parentId 父菜单id
     * @param menuName 菜单名
     * @param menuType 菜单类型
     * @param menuId   菜单id
     */
    private void verifyMenu(Long parentId, String menuName, Integer menuType, Long menuId) {
        //校验菜单类型
        verifyMenuType(menuType);

        //菜单已存在（当前父菜单下不能有相同的菜单名，所以根据菜单名匹配）
        Menu menu = menuMapper.selectOne(parentId, menuName);
        //找不到说明菜单不存在，而且是创建菜单操作，所以直接return结束
        if (Objects.isNull(menu)) {
            return;
        }

        //如果找到了menu，但是menuId为空，说明是创建菜单操作，但是菜单已存在，所以抛出异常
        if (Objects.isNull(menuId)) {
            throw new BusinessException(MENU_ALREADY_EXISTS.getCode(), MENU_ALREADY_EXISTS.getMessage());
        }

        //在更新菜单时菜单id要保持一致,不一致说明有相同菜单
        if (!menu.getId().equals(menuId)) {
            throw new BusinessException(MENU_PRIMARY_KEY_ID_ERROR.getCode(), MENU_PRIMARY_KEY_ID_ERROR.getMessage());
        }
    }

    /**
     * 校验菜单类型是否合法，必须是：目录、菜单、按钮
     *
     * @param menuType 菜单类型
     */
    private void verifyMenuType(Integer menuType) {
        Set<Integer> menuTypes = Arrays.stream(MenuTypeEnum.values()).map(MenuTypeEnum::getCode).collect(Collectors.toSet());
        if (!menuTypes.contains(menuType)) {
            throw new BusinessException(MENU_TYPE_ERROR.getCode(), MENU_TYPE_ERROR.getMessage());
        }
    }

    /**
     * 校验父菜单是否合法，在create or update时使用
     *
     * @param parentId    父菜单id
     * @param childMenuId create or update时的菜单id
     */
    private void verifyParentMenu(Long parentId, Long childMenuId) {
        //父菜单id为空或者等于ROOT菜单id直接跳过
        if (Objects.isNull(parentId) || ROOT.equals(parentId)) {
            return;
        }

        //父菜单不能是自己
        if (parentId.equals(childMenuId)) {
            throw new BusinessException(MENU_SET_PARENT_MENU_ERROR.getCode(), MENU_SET_PARENT_MENU_ERROR.getMessage());
        }

        //父菜单不存在
        Menu parentMenu = menuMapper.selectOne(Menu::getId, parentId);
        if (Objects.isNull(parentMenu)) {
            throw new BusinessException(MENU_PARENT_NOT_EXISTS.getCode(), MENU_PARENT_NOT_EXISTS.getMessage());
        }

        //父菜单类型必须是目录或者菜单
        if (!MenuTypeEnum.DIR.getCode().equals(parentMenu.getType())
                && !MenuTypeEnum.MENU.getCode().equals(parentMenu.getType())) {
            throw new BusinessException(MENU_TYPE_ERROR.getCode(), MENU_TYPE_ERROR.getMessage());
        }
    }
}

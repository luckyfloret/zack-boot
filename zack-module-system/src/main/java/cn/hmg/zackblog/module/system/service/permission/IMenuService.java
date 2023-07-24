package cn.hmg.zackblog.module.system.service.permission;

import cn.hmg.zackblog.module.system.controller.admin.permission.vo.menu.MenuCreateReqVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.menu.MenuListReqVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.menu.MenuUpdateReqVO;
import cn.hmg.zackblog.module.system.entity.permission.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 后台系统菜单 服务类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 初始化本地缓存
     */
    void initLocalCache();

    /**
     * 根据menuId从缓存中获取菜单信息
     * @param menuId 菜单id
     * @return Menu
     */
    Menu getMenuByIdFromCache(Long menuId);

    /**
     * 根据menuIds从缓存中获取菜单列表
     * @param menuIds 菜单id集合
     * @return 菜单列表
     */
    List<Menu> getMenuListByIdsFromCache(Set<Long> menuIds);

    /**
     *  获取菜单列表
     * @param menuListReqVO 菜单列表请求参数
     * @return 菜单列表
     */
    List<Menu> getMenuList(MenuListReqVO menuListReqVO);

    /**
     * 创建菜单
     * @param menuCreateReqVO 创建菜单请求参数
     */
    void createMenu(MenuCreateReqVO menuCreateReqVO);

    /**
     *  根据菜单id更新菜单
     * @param menuUpdateReqVO 更新菜单请求参数
     */
    void updateMenu(MenuUpdateReqVO menuUpdateReqVO);

    /**
     * 根据菜单id删除菜单
     * @param menuId 菜单id
     */
    void deleteMenuById(Long menuId);
}

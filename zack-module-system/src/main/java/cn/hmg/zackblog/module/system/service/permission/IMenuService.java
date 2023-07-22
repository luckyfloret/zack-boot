package cn.hmg.zackblog.module.system.service.permission;

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
}

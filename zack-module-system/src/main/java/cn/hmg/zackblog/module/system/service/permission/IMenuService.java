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

    Menu getMenuByIdFromCache(Long menuId);

    List<Menu> getMenuListByIdsFromCache(Set<Long> menuIds);
}

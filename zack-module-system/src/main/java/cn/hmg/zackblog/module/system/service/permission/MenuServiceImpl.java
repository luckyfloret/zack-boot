package cn.hmg.zackblog.module.system.service.permission;

import cn.hmg.zackblog.common.utils.collections.CollectionUtils;
import cn.hmg.zackblog.module.system.entity.permission.Menu;
import cn.hmg.zackblog.module.system.mapper.permission.MenuMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    private void initMenuCache(){
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

}

package cn.hmg.zackblog.module.system.service.permission;

import cn.hmg.zackblog.common.utils.collections.CollectionUtils;
import cn.hmg.zackblog.module.system.entity.permission.Role;
import cn.hmg.zackblog.module.system.mapper.permission.RoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色管理 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    /**
     * 角色信息的缓存
     * 使用volatile关键字修饰是为了在高并发场景下保证变量的可见性，有更新立即从主存中读取
     */
    private volatile Map<Long, Role> roleCache;

    @Resource
    private RoleMapper roleMapper;


    private void initRoleCache(){
        List<Role> roles = roleMapper.selectList();
        log.info("[initRoleCache] => 初始化角色信息缓存，数量为：{}", roles.size());
        roleCache = CollectionUtils.convertMap(roles, Role::getId);
    }


    @PostConstruct
    @Override
    public void initLocalCache() {
        initRoleCache();
    }

    @Override
    public Role getRoleByIdFromCache(Long roleId) {
        return roleCache.get(roleId);
    }
}

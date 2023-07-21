package cn.hmg.zackblog.module.system.service.permission;

import cn.hmg.zackblog.module.system.entity.permission.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色管理 服务类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
public interface IRoleService extends IService<Role> {

    /**
     * 初始化本地缓存
     */
    void initLocalCache();

    /**
     * 根据角色id从缓存中获取角色信息
     * @param roleId 角色id
     * @return Role
     */
    Role getRoleByIdFromCache(Long roleId);
}

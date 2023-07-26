package cn.hmg.zackblog.module.system.mapper.permission;

import cn.hmg.zackblog.framework.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.framework.core.query.LambdaQueryWrapperExtend;
import cn.hmg.zackblog.module.system.entity.permission.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;

/**
 * <p>
 * 用户和角色的关联表 Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Mapper
public interface UserRoleMapper extends BaseMapperExtend<UserRole> {

    default void deleteByRoleId(Long roleId) {
        delete(UserRole::getRoleId, roleId);
    }

    default void deleteByUserId(Long userId) {
        delete(UserRole::getUserId, userId);
    }

    default void deleteByUserId(Long userId, Collection<Long> roleIds) {
        delete(new LambdaQueryWrapperExtend<UserRole>().eq(UserRole::getUserId, userId).in(UserRole::getRoleId, roleIds));
    }
}

package cn.hmg.zackblog.module.system.mapper.permission;

import cn.hmg.zackblog.framework.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.framework.core.query.LambdaQueryWrapperExtend;
import cn.hmg.zackblog.module.system.entity.permission.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 角色和菜单的关联表 Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Mapper
public interface RoleMenuMapper extends BaseMapperExtend<RoleMenu> {
    /**
     * 根据菜单id查询角色菜单列表
     * @param menuId 菜单id
     * @return List<RoleMenu>
     */
    default List<RoleMenu> selectList(Long menuId){
        return selectList(new LambdaQueryWrapperExtend<RoleMenu>().eqIfExists(RoleMenu::getMenuId, menuId));
    }

    default void deleteByRoleId(Long roleId) {
        delete(RoleMenu::getRoleId, roleId);
    }

    default List<RoleMenu> selectListByRoleId(Long roleId) {
        return selectList(new LambdaQueryWrapperExtend<RoleMenu>().eq(RoleMenu::getRoleId, roleId));
    }

    default void deleteByRoleId(Long roleId, Collection<Long> deleteMenuIds) {
        delete(new LambdaQueryWrapperExtend<RoleMenu>().eq(RoleMenu::getRoleId, roleId).in(RoleMenu::getMenuId, deleteMenuIds));
    }

    default void deleteByMenuId(Long menuId) {
        delete(new LambdaQueryWrapperExtend<RoleMenu>().eq(RoleMenu::getMenuId, menuId));
    }
}

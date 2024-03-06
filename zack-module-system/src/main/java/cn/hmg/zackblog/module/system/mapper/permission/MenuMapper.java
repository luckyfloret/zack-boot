package cn.hmg.zackblog.module.system.mapper.permission;

import cn.hmg.zackblog.framework.mybatisplus.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.framework.mybatisplus.core.query.LambdaQueryWrapperExtend;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.menu.MenuListReqVO;
import cn.hmg.zackblog.module.system.entity.permission.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 后台系统菜单 Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Mapper
public interface MenuMapper extends BaseMapperExtend<Menu> {
    /**
     * 根据菜单状态获取菜单列表
     * @param status 菜单状态
     * @return 菜单列表
     */
    default List<Menu> selectList(Integer status){
        return selectList(new LambdaQueryWrapperExtend<Menu>().eq(Menu::getStatus, status));
    }

    /**
     * 获取菜单列表，请求参数[可有可无]
     * @param reqVO 请求参数
     * @return 菜单列表
     */
    default List<Menu> selectList(MenuListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperExtend<Menu>()
                .eqIfExists(Menu::getStatus, reqVO.getStatus())
        );
    }

    /**
     * 根据菜单id查询菜单
     * @param menuId 菜单id
     * @return Menu
     */
    default Menu selectOne(Long menuId) {
        return selectOne(Menu::getId, menuId);
    }

    /**
     * 根据父菜单id和名称查询菜单
     * @param parentId 父菜单id
     * @param name 菜单名称
     * @return Menu
     */
    default Menu selectOne(Long parentId, String name) {
        return selectOne(Menu::getParentId, parentId, Menu::getName, name);
    }

    /**
     * 根据父菜单id查询菜单数量
     * @param menuId 父菜单id
     * @return 菜单数量
     */
    default Long selectCountByParentId(Long menuId){
        return selectCount(Menu::getParentId, menuId);
    }
}

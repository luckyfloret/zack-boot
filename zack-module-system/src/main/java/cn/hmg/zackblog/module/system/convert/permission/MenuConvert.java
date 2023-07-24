package cn.hmg.zackblog.module.system.convert.permission;

import cn.hmg.zackblog.module.system.controller.admin.permission.vo.menu.MenuCreateReqVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.menu.MenuRespVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.menu.MenuUpdateReqVO;
import cn.hmg.zackblog.module.system.entity.permission.Menu;
import cn.hutool.core.util.ObjUtil;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

import static cn.hmg.zackblog.module.system.entity.permission.Menu.ROOT;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-23 11:03
 * @description: 菜单convert接口
 */
@Mapper
public interface MenuConvert {
    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);


    /**
     * 将菜单转换称菜单 response vo
     *
     * @param menu 菜单
     * @return MenuRespVO
     */
    MenuRespVO convertMenuRespVO(Menu menu);


    /**
     * 转换菜单树
     *
     * @param menuList 菜单列表
     * @return MenuRespVO
     */
    default List<MenuRespVO> buildMenuTree(List<Menu> menuList) {
        //排序
        menuList.sort(Comparator.comparing(Menu::getSort));
        //构建Map
        Map<Long, MenuRespVO> nodeMap =
                menuList.stream().map(this::convertMenuRespVO).collect(Collectors.toMap(MenuRespVO::getId, menuRespVO -> menuRespVO));

        menuList.stream().filter(node -> ObjUtil.notEqual(ROOT, node.getParentId())).forEach(node -> {
            Long parentId = node.getParentId();
            MenuRespVO parentNode = nodeMap.get(parentId);
            MenuRespVO childrenNode = nodeMap.get(node.getId());
            if (Objects.isNull(parentNode)) {
                LoggerFactory.getLogger(getClass()).error("找不到父节点， parent id => {}, children id => {}", parentId, childrenNode.getId());
                return;
            }

            if (Objects.isNull(parentNode.getChildren())) {
                parentNode.setChildren(new ArrayList<>());
            }

            //将子节点添加到父节点
            parentNode.getChildren().add(childrenNode);
        });

//        最后取出所有根节点
        return nodeMap.values().stream().filter(node -> ROOT.equals(node.getParentId())).collect(Collectors.toList());
    }

    /**
     * 创建菜单请求参数转换为菜单
     * @param menuCreateReqVO 创建菜单请求参数
     * @return Menu
     */
    Menu convertMenu(MenuCreateReqVO menuCreateReqVO);

    /**
     * 更新菜单请求参数转换为菜单
     * @param menuUpdateReqVO 更新菜单请求参数
     * @return Menu
     */
    Menu convertMenu(MenuUpdateReqVO menuUpdateReqVO);
}

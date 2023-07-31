package cn.hmg.zackblog.module.system.convert.auth;

import cn.hmg.zackblog.framework.security.core.pojo.LoginUser;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.AdminAuthLoginRespVO;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.AdminAuthMenuRespVO;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.AdminAuthPermissionRespVO;
import cn.hmg.zackblog.module.system.entity.permission.Menu;
import cn.hmg.zackblog.module.system.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

import static cn.hmg.zackblog.module.system.entity.permission.Menu.ROOT;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-15 15:52
 * @description: admin 认证convert接口
 */
@Mapper
public interface AdminAuthConvert {
    AdminAuthConvert INSTANCE = Mappers.getMapper(AdminAuthConvert.class);


    /**
     * 登录用户转换为登录 response vo
     * @param loginUser 登录用户
     * @return AdminAuthLoginRespVO
     */
    @Mapping(target = "expireTime", source = "refreshTokenExpireTime")
    AdminAuthLoginRespVO convert(LoginUser loginUser);

    /**
     * 菜单转换为用户菜单导航
     * @param menu 菜单
     * @return AdminAuthMenuRespVO
     */
    AdminAuthMenuRespVO convertAdminAuthMenuRespVO(Menu menu);

    /**
     * 创建一个Map对象，用于存储每个节点的ID和对应的节点对象。
     * 遍历数据集，为每个节点创建一个对应的节点对象，并将其放入Map中，以节点ID作为键。
     * 遍历数据集，为每个节点找到其父节点，并将其添加到父节点的子节点列表中。
     * 根据根节点的ID，从Map中获取根节点对象，即可得到完整的树形结构。
     *
     * @param menuListFromCache 菜单列表
     * @return AdminAuthMenuRespVO
     */
    default List<AdminAuthMenuRespVO> buildMenuTree(List<Menu> menuListFromCache) {
        //先排序
        menuListFromCache.sort(Comparator.comparing(Menu::getSort));

        //把menuListFromCache转成Map，key为menuId，value为menu对象
        Map<Long, AdminAuthMenuRespVO> nodeMaps = menuListFromCache.stream().map(this::convertAdminAuthMenuRespVO).collect(Collectors.toMap(AdminAuthMenuRespVO::getId, menu -> menu));

        //把每个子节点添加到父节点中
        menuListFromCache.stream().filter(node -> !ROOT.equals(node.getParentId())).forEach(menu -> {
            Long parentId = menu.getParentId();
            AdminAuthMenuRespVO parentNode = nodeMaps.get(parentId);
            AdminAuthMenuRespVO childrenNode = nodeMaps.get(menu.getId());
            if (Objects.isNull(parentNode)) {
                LoggerFactory.getLogger(getClass()).error("[buildMenuTree] => 没有找到父节点，父节点id为：{}， 子节点id为：{}", childrenNode.getParentId(), childrenNode.getId());
                return;
            }

            if (Objects.isNull(parentNode.getChildren())) {
                parentNode.setChildren(new ArrayList<>());
            }
            parentNode.getChildren().add(childrenNode);
        });

        //最后取出所有根节点
        return nodeMaps.values().stream().filter(node -> ROOT.equals(node.getParentId())).collect(Collectors.toList());
    }


    default AdminAuthPermissionRespVO convertAdminAuthPermissionRespVO(User user, Set<String> permissions){
        return AdminAuthPermissionRespVO.builder()
                .userVO(AdminAuthPermissionRespVO.UserVO.builder().userId(user.getId()).nickname(user.getNickname()).avatar(user.getAvatar()).build())
                .permissions(permissions).build();
    }
}

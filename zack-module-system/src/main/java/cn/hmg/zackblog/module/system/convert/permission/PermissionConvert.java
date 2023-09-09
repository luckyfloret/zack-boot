package cn.hmg.zackblog.module.system.convert.permission;

import cn.hmg.zackblog.module.system.controller.admin.permission.vo.permission.PermissionMenuListRespVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.permission.PermissionRoleListRespVO;
import cn.hmg.zackblog.module.system.entity.permission.Menu;
import cn.hmg.zackblog.module.system.entity.permission.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-25 23:31
 * @description: 权限convert
 */
@Mapper
public interface PermissionConvert {
    PermissionConvert INSTANCE = Mappers.getMapper(PermissionConvert.class);

    List<PermissionMenuListRespVO> convert(List<Menu> menuList);

    List<PermissionRoleListRespVO> convertPermissionRoleListRespVO(List<Role> roleList);
}

package cn.hmg.zackblog.module.system.convert.permission;

import cn.hmg.zackblog.module.system.controller.admin.permission.vo.role.RoleCreateReqVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.role.RolePageRespVO;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.role.RoleUpdateReqVO;
import cn.hmg.zackblog.module.system.entity.permission.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-25 1:22
 * @description:
 */
@Mapper
public interface RoleConvert {
    RoleConvert INSTANCE = Mappers.getMapper(RoleConvert.class);

    RolePageRespVO convert(Role role);

    default List<RolePageRespVO> convert(List<Role> roleList){
        return roleList.stream().map(this::convert).collect(Collectors.toList());
    }

    Role convert(RoleCreateReqVO roleCreateReqVO);

    Role convert(RoleUpdateReqVO roleUpdateReqVO);
}

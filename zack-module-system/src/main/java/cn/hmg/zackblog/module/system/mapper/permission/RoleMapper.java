package cn.hmg.zackblog.module.system.mapper.permission;

import cn.hmg.zackblog.framework.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.module.system.entity.permission.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色管理 Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Mapper
public interface RoleMapper extends BaseMapperExtend<Role> {

}

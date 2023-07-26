package cn.hmg.zackblog.module.system.mapper.permission;

import cn.hmg.zackblog.common.pojo.PageResult;
import cn.hmg.zackblog.framework.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.framework.core.query.LambdaQueryWrapperExtend;
import cn.hmg.zackblog.module.system.controller.admin.permission.vo.role.RolePageReqVO;
import cn.hmg.zackblog.module.system.entity.permission.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
    default PageResult<Role> getPage(RolePageReqVO reqVO) {
        return page(reqVO, new LambdaQueryWrapperExtend<Role>()
                .likeIfExists(Role::getName, reqVO.getName())
                .eqIfExists(Role::getStatus, reqVO.getStatus())
        );
    }

    default Role selectByRoleName(String roleName) {
        return selectOne(Role::getName, roleName);
    }

    default Role selectOne(Long roleId) {
        return selectOne(Role::getId, roleId);
    }

    default Role selectOne(String code) {
        return selectOne(Role::getCode, code);
    }

    default List<Role> selectListByStatus(Integer status) {
        return selectList(new LambdaQueryWrapperExtend<Role>().eq(Role::getStatus, status));
    }
}

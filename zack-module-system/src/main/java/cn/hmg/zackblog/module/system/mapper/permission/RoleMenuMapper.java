package cn.hmg.zackblog.module.system.mapper.permission;

import cn.hmg.zackblog.module.system.entity.permission.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色和菜单的关联表 Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

}

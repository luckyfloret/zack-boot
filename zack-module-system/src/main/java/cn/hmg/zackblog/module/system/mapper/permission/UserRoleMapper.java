package cn.hmg.zackblog.module.system.mapper.permission;

import cn.hmg.zackblog.module.system.entity.permission.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户和角色的关联表 Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}

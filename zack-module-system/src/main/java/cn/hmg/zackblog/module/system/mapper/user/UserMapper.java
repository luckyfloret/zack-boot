package cn.hmg.zackblog.module.system.mapper.user;

import cn.hmg.zackblog.framework.core.mapper.BaseMapperExtend;
import cn.hmg.zackblog.module.system.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户管理 Mapper 接口
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Mapper
public interface UserMapper extends BaseMapperExtend<User> {

}

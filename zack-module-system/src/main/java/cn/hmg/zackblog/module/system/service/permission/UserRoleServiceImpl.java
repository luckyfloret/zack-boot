package cn.hmg.zackblog.module.system.service.permission;

import cn.hmg.zackblog.module.system.entity.permission.UserRole;
import cn.hmg.zackblog.module.system.mapper.permission.UserRoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户和角色的关联表 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}

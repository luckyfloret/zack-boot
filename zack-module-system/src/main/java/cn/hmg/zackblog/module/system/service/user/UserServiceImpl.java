package cn.hmg.zackblog.module.system.service.user;

import cn.hmg.zackblog.common.exception.ServiceException;
import cn.hmg.zackblog.module.system.entity.user.User;
import cn.hmg.zackblog.module.system.mapper.user.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * <p>
 * 用户管理 服务实现类
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> getUserByUsername(String username) {
        return Optional.ofNullable(userMapper.selectByUsername(username));
    }

    @Override
    public boolean ifPasswordMatch(String password, String encodePassword) {
        return passwordEncoder.matches(password, encodePassword);
    }
}

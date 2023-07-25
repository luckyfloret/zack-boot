package cn.hmg.zackblog.module.system.service.user;

import cn.hmg.zackblog.module.system.entity.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-15 16:56
 * @description:
 */
@SpringBootTest
public class UserServiceImplTest {

    @Resource
    private IUserService userService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Test
    public void updatePassword() {
        User user = new User();
        user.setId(1L);
        user.setPassword(passwordEncoder.encode("123456"));
        userService.updateById(user);
    }
}

package cn.hmg.zackblog.module.system.service.auth;

import cn.hmg.zackblog.main.ZackMainApplication;
import cn.hmg.zackblog.framework.common.enums.UserTypeEnum;
import cn.hmg.zackblog.framework.security.core.pojo.LoginUser;
import cn.hmg.zackblog.framework.redis.core.utils.RedisUtils;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.AdminAuthLoginReqVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-14 16:11
 * @description: 认证测试类
 */
@SpringBootTest(classes = ZackMainApplication.class)
@ExtendWith(value = SpringExtension.class)
public class AuthServiceImplTest {
    @Resource
    private AuthServiceImpl authService;

    @Resource
    private RedisUtils redisUtils;


    @Test
    public void redisUtilTest(){
        String s = redisUtils.get("sdfsd");
        System.out.println(s);


        LoginUser loginUser = redisUtils.get("dsjfsdsfd", LoginUser.class);
        System.out.println(loginUser);
    }

    @Test
    public void testAuthentication(){
        AdminAuthLoginReqVO adminAuthLoginReqVO = new AdminAuthLoginReqVO();
        adminAuthLoginReqVO.setUsername("sdfdsfsd");
        authService.authentication(adminAuthLoginReqVO, UserTypeEnum.ADMIN_USER);
    }
}

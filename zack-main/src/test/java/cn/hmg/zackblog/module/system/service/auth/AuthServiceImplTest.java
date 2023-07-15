package cn.hmg.zackblog.module.system.service.auth;

import cn.hmg.zackblog.ZackMainApplication;
import cn.hmg.zackblog.common.enums.UserTypeEnum;
import cn.hmg.zackblog.module.system.controller.admin.auth.vo.AdminAuthLoginReqVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-14 16:11
 * @description: 认证测试类
 */
@SpringBootTest(classes = ZackMainApplication.class)
public class AuthServiceImplTest {
    @Resource
    private AuthServiceImpl authService;

    @Test
    public void testAuthentication(){
        AdminAuthLoginReqVO adminAuthLoginReqVO = new AdminAuthLoginReqVO();
        adminAuthLoginReqVO.setUsername("sdfdsfsd");
        authService.authentication(adminAuthLoginReqVO, UserTypeEnum.ADMIN_USER);
    }
}

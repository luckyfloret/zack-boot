package cn.hmg.zackblog.module.system.controller.admin.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-26 19:54
 * @description: 用户中心 or 个人中心
 */
@Tag(name = "后台-用户中心 or 个人中心")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/system/user-center")
public class UserCenterController {

}

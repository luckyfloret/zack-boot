package cn.hmg.zackblog.module.system.controller.admin.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-18 10:26
 * @description: 权限编码 response vo
 */
@Schema(name = "权限编码 response vo")
@Data
@Builder
public class AdminAuthPermissionRespVO {

    private UserVO userVO;

    private Set<String> permissions;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserVO{
        //用户id
        private Long userId;

        //昵称
        private String nickname;

        //用户头像
        private String avatar;
    }
}

package cn.hmg.zackblog.module.system.controller.admin.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-18 16:36
 * @description: 用户重置密码 request vo
 */
@Data
@Schema(name = "用户重置密码 request vo")
public class UserResetPasswordReqVO {
    @NotNull(message = "用户id不能为空")
    @Schema(description = "用户id")
    private Long userId;
}

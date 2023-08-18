package cn.hmg.zackblog.module.system.controller.admin.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-26 20:11
 * @description: 用户更新密码 request vo
 */
@Schema(name = "用户更新密码 request vo")
@Data
public class UserUpdatePasswordReqVO {
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @NotBlank(message = "旧密码不能为空")
    @Schema(description = "旧密码")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    @Schema(description = "新密码")
    private String newPassword;
}

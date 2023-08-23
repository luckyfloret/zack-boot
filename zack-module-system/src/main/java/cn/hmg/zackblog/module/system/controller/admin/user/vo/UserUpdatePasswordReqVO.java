package cn.hmg.zackblog.module.system.controller.admin.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
    @NotBlank(message = "旧密码不能为空")
    @Schema(description = "旧密码")
    @Length(max = 20, message = "最大长度为20位")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    @Schema(description = "新密码")
    @Length(max = 20, message = "最大长度为20位")
    private String newPassword;
}

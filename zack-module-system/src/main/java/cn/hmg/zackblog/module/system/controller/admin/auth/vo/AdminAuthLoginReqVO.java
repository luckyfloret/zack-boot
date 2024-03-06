package cn.hmg.zackblog.module.system.controller.admin.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-08 14:25
 * @description: 登录request vo
 */
@Schema(name = "登录request vo", description = "登录request vo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminAuthLoginReqVO {
    @Schema(description = "用户名", required = true, example = "admin")
    @NotBlank
    private String username;


    @Schema(description = "密码", required = true, example = "admin")
    @NotBlank
    private String password;

    @Schema(description = "验证码", required = true, example = "fdjsidfhsdofhsdfsdjl")
    private String captchaVerification;
}

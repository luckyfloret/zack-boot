package cn.hmg.zackblog.module.system.controller.admin.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "登录request vo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginReqVO {
    @ApiModelProperty(value = "用户名", required = true, example = "admin")
    @NotBlank
    private String username;


    @ApiModelProperty(value = "密码", required = true, example = "admin")
    @NotBlank
    private String password;
}

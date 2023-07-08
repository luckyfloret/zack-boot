package cn.hmg.zackblog.module.system.controller.admin.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-08 11:52
 * @description: 登录 response VO
 */
@ApiModel(value = "登录 response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRespVO {
    @ApiModelProperty(value = "用户id", required = true, example = "1")
    private Long userId;

    @ApiModelProperty(value = "访问令牌", required = true, example = "exampleAccessToken")
    private String accessToken;

    @ApiModelProperty(value = "刷新令牌", required = true, example = "exampleRefreshToken")
    private String refreshToken;

    @ApiModelProperty(value = "过期时间", required = true, example = "2023-07-08 00:00:00")
    private LocalDateTime expireTime;
}

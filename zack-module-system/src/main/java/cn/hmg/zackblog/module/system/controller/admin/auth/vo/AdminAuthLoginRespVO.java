package cn.hmg.zackblog.module.system.controller.admin.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-08 11:52
 * @description: 登录 response VO
 */
@Schema(name = "登录 response VO", description = "登录 response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminAuthLoginRespVO {
    @Schema(description = "用户id", required = true, example = "1")
    private Long userId;

    @Schema(description = "访问令牌", required = true, example = "exampleAccessToken")
    private String accessToken;

    @Schema(description = "刷新令牌", required = true, example = "exampleRefreshToken")
    private String refreshToken;

    @Schema(description = "过期时间", required = true, example = "2023-07-08 00:00:00")
    private LocalDateTime expireTime;
}

package cn.hmg.zackblog.module.system.controller.admin.logger.vo.loginlog;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-23 15:24
 * @description: 登录日志基础vo
 */
@Data
public class BaseLoginLogVO {
    @Schema(description = "用户账号")
    private String username;

    @Schema(description = "登录类型（1. 账号登录，2. 邮箱登录）")
    private Integer loginType;

    @Schema(description = "用户ip")
    private String userIp;

    @Schema(description = "用户类型")
    private Integer userType;

    @Schema(description = "登录结果")
    private Integer result;

    @Schema(description = "登录时间")
    private LocalDateTime createTime;
}

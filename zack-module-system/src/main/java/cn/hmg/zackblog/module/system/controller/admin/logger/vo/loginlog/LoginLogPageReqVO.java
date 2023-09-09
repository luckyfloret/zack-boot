package cn.hmg.zackblog.module.system.controller.admin.logger.vo.loginlog;

import cn.hmg.zackblog.framework.common.pojo.PageQueryParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-22 23:48
 * @description: 登录日志分页 request vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "登录日志分页 request vo")
public class LoginLogPageReqVO extends PageQueryParam {
    @Schema(description = "用户类型")
    private String userType;

    @Schema(description = "登录ip")
    private String userIp;

    @Schema(description = "用户账号")
    private String username;

    @Schema(description = "登录结果")
    private Boolean result;

    @Schema(description = "登录时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}

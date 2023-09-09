package cn.hmg.zackblog.module.system.controller.admin.logger.vo.loginlog;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-22 23:49
 * @description: 登录日志分页 response vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "登录日志分页 response vo")
public class LoginLogPageRespVO extends BaseLoginLogVO{
    @Schema(description = "id(主键）")
    private Long id;
}

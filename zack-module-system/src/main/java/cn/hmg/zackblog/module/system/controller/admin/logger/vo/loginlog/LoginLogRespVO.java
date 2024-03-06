package cn.hmg.zackblog.module.system.controller.admin.logger.vo.loginlog;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-22 23:49
 * @description: 登录日志 response vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "登录日志 response vo")
public class LoginLogRespVO extends BaseLoginLogVO{
    @Schema(description = "id(主键）")
    private Long id;

    @Schema(description = "用户账号")
    private String username;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "浏览器UA")
    private String userAgent;
}

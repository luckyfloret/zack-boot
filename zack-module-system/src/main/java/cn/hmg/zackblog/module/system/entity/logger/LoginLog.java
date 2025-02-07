package cn.hmg.zackblog.module.system.entity.logger;

import cn.hmg.zackblog.framework.mybatisplus.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 登录日志
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("system_login_log")
@Schema(name = "LoginLog对象", description = "登录日志")
public class LoginLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id(主键）")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户账号")
    private String username;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "登录类型（1. 账号登录，2. 邮箱登录）")
    private Integer loginType;

    @Schema(description = "用户类型（前台用户，后台用户）")
    private Integer userType;

    @Schema(description = "用户ip")
    private String userIp;

    @Schema(description = "浏览器UA")
    private String userAgent;

    @Schema(description = "登录结果 (200 成功，其他失败）")
    private Integer result;

    @Schema(description = "用户id")
    private Long userId;
}

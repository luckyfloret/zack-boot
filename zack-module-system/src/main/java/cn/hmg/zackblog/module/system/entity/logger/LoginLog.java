package cn.hmg.zackblog.module.system.entity.logger;

import cn.hmg.zackblog.framework.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 登录日志
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Getter
@Setter
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
    private Byte userType;

    @Schema(description = "用户ip")
    private String userIp;

    @Schema(description = "浏览器UA")
    private String userAgent;

    @Schema(description = "登录结果 （0 成功， 1 失败）")
    private Byte result;

    @Schema(description = "用户id")
    private Long userId;
}

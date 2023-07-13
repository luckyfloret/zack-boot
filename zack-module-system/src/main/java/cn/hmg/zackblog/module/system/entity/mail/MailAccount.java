package cn.hmg.zackblog.module.system.entity.mail;

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
 * 邮件账号管理
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Getter
@Setter
@TableName("system_mail_account")
@Schema(name = "MailAccount对象", description = "邮件账号管理")
public class MailAccount extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id （主键）")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "SMTP端口")
    private String port;

    @Schema(description = "SMTP域名")
    private String host;

    @Schema(description = "是否开启ssl （0 false ，1 true）， 默认开启")
    private Boolean sslEnable;
}

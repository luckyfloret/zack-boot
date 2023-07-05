package cn.hmg.zackblog.module.system.entity.mail;

import cn.hmg.zackblog.framework.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "MailAccount对象", description = "邮件账号管理")
public class MailAccount extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id （主键）")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("SMTP端口")
    private String port;

    @ApiModelProperty("SMTP域名")
    private String host;

    @ApiModelProperty("是否开启ssl （0 false ，1 true）， 默认开启")
    private Boolean sslEnable;
}

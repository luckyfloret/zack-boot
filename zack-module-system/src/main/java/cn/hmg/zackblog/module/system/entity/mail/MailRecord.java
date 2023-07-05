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
import java.time.LocalDateTime;

/**
 * <p>
 * 邮件记录
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Getter
@Setter
@TableName("system_mail_record")
@ApiModel(value = "MailRecord对象", description = "邮件记录")
public class MailRecord extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id （主键）")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户类型（1 前台用户， 2 后台用户）")
    private Byte userType;

    @ApiModelProperty("邮箱账号id")
    private Long accountId;

    @ApiModelProperty("接收邮箱地址")
    private String toMail;

    @ApiModelProperty("发送邮箱地址")
    private String fromMail;

    @ApiModelProperty("模板id")
    private Long templateId;

    @ApiModelProperty("模板发送人名称")
    private String templateNickname;

    @ApiModelProperty("邮件模板标题")
    private String templateTitle;

    @ApiModelProperty("邮件模板内容")
    private String templateContent;

    @ApiModelProperty("邮件参数")
    private String templateParams;

    @ApiModelProperty("发送状态（0 发送失败， 1 发送成功）")
    private Byte sendStatus;

    @ApiModelProperty("发送时间")
    private LocalDateTime sendTime;

    @ApiModelProperty("发送异常")
    private String sendException;
}

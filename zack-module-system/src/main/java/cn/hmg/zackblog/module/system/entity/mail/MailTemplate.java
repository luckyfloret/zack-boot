package cn.hmg.zackblog.module.system.entity.mail;

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
 * 邮件模板
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Getter
@Setter
@TableName("system_mail_template")
@ApiModel(value = "MailTemplate对象", description = "邮件模板")
public class MailTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id （主键）")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("用户名称")
    private String nickname;

    @ApiModelProperty("邮箱账户id")
    private Long accountId;

    @ApiModelProperty("模板标题")
    private String title;

    @ApiModelProperty("模板内容")
    private String content;

    @ApiModelProperty("参数数组")
    private String params;

    @ApiModelProperty("状态（0 关闭，1 开启 ）")
    private Byte status;

    @ApiModelProperty("备注")
    private String remark;
}

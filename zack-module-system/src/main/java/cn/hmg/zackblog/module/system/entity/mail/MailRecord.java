package cn.hmg.zackblog.module.system.entity.mail;

import cn.hmg.zackblog.framework.mybatisplus.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 * 邮件记录
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "system_mail_record", autoResultMap = true)
@Schema(name = "MailRecord对象", description = "邮件记录")
public class MailRecord extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id （主键）")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "用户类型（1 前台用户， 2 后台用户）")
    private Integer userType;

    @Schema(description = "邮箱账号id")
    private Long accountId;

    @Schema(description = "接收邮箱地址")
    private String toMail;

    @Schema(description = "发送邮箱地址")
    private String fromMail;

    @Schema(description = "模板id")
    private Long templateId;

    @Schema(description = "模板编码")
    private String templateCode;

    @Schema(description = "模板发送人名称")
    private String senderNickname;

    @Schema(description = "邮件模板标题")
    private String templateTitle;

    @Schema(description = "邮件模板内容")
    private String templateContent;

    @Schema(description = "邮件参数")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> templateParams;

    @Schema(description = "发送状态（0 发送失败， 1 发送成功）")
    private Integer sendStatus;

    @Schema(description = "发送时间")
    private LocalDateTime sendTime;

    @Schema(description = "发送异常")
    private String sendException;
}

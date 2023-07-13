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
 * 邮件模板
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Getter
@Setter
@TableName("system_mail_template")
@Schema(name = "MailTemplate对象", description = "邮件模板")
public class MailTemplate extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id （主键）")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "用户名称")
    private String nickname;

    @Schema(description = "邮箱账户id")
    private Long accountId;

    @Schema(description = "模板标题")
    private String title;

    @Schema(description = "模板内容")
    private String content;

    @Schema(description = "参数数组")
    private String params;

    @Schema(description = "状态（0 关闭，1 开启 ）")
    private Byte status;

    @Schema(description = "备注")
    private String remark;
}

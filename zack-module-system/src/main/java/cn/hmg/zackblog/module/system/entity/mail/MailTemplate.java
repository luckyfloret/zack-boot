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
import java.util.List;

/**
 * <p>
 * 邮件模板
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "system_mail_template", autoResultMap = true)
@Schema(name = "MailTemplate对象", description = "邮件模板")
public class MailTemplate extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id （主键）")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "模板编码")
    private String code;

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
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> params;

    @Schema(description = "状态（0 开启，1 禁用 ）")
    private Integer status;

    @Schema(description = "备注")
    private String remark;
}

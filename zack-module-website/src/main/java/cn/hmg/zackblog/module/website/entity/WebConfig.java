package cn.hmg.zackblog.module.website.entity;

import cn.hmg.zackblog.framework.mybatisplus.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 网站配置
 * </p>
 *
 * @author hmg
 * @since 2023-08-30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("biz_web_config")
@Schema(name = "WebConfig对象", description = "网站配置")
public class WebConfig extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id 主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "网站标题")
    private String title;

    @Schema(description = "网站名称")
    private String name;

    @Schema(description = "网站logo（目前用不上，作为保留字段）")
    private String logo;

    @Schema(description = "网站地址")
    private String websiteUrl;

    @Schema(description = "网站描述")
    private String description;

    @Schema(description = "备案号")
    private String recordNumber;
    @Schema(description = "作者名称")
    private String authorName;

    @Schema(description = "作者简介")
    private String authorSummary;

    @Schema(description = "作者头像")
    private String authorAvatar;

    @Schema(description = "关于作者")
    private String aboutAuthor;

    @Schema(description = "支付宝收款码")
    private String alipay;

    @Schema(description = "微信收款码")
    private String wechatPay;

    @Schema(description = "是否开启评论")
    private Integer isEnabledComment;

    @Schema(description = "是否开启打赏")
    private Integer isEnabledTipping;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "gitee地址")
    private String giteeUrl;

    @Schema(description = "github地址")
    private String githubUrl;
}

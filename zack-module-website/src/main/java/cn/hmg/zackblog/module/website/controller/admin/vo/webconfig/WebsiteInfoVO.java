package cn.hmg.zackblog.module.website.controller.admin.vo.webconfig;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-04 10:20
 * @description: 网站信息vo
 */
@Data
@Schema(name = "网站信息vo")
public class WebsiteInfoVO {
    @Schema(description = "id")
    private Long id;

    @Schema(description = "网站标题")
    @NotBlank(message = "网站标题不能为空")
    private String title;

    @Schema(description = "网站名称")
    @NotBlank(message = "网站名称不能为空")
    private String name;

    @Schema(description = "网站地址")
    private String websiteUrl;

    @Schema(description = "网站描述")
    private String description;

    @Schema(description = "备案号")
    private String recordNumber;

    @Schema(description = "支付宝收款码")
    private String alipay;

    @Schema(description = "微信收款码")
    private String wechatPay;

    @Schema(description = "是否开启评论")
    @NotNull(message = "评论不能为空")
    private Integer isEnabledComment;

    @Schema(description = "是否开启打赏")
    @NotNull(message = "打赏不能为空")
    private Integer isEnabledTipping;
}

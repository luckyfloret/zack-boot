package cn.hmg.zackblog.module.website.controller.admin.vo.friendlink;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-30 14:07
 * @description: 基础友情链接 vo
 */
@Data
@Schema(description = "基础友情链接 vo")
public class BaseFriendLinkVO {
    @Schema(description = "网站名称")
    @NotBlank(message = "网站名称不能为空")
    private String name;

    @Schema(description = "网站简介")
    private String description;

    @Schema(description = "网站头像url")
    @NotBlank(message = "网站头像url不能为空")
    @URL(message = "请输入正确的头像URL")
    private String avatarUrl;

    @Schema(description = "邮箱地址")
    @NotBlank(message = "邮箱地址不能为空")
    @Email(message = "请输入正确的邮箱")
    private String email;

    @Schema(description = "网站地址")
    @NotBlank(message = "网站地址不能为空")
    @URL(message = "请输入正确的网站地址")
    private String websiteUrl;

    @Schema(description = "排序")
    @NotNull(message = "排序不能为空")
    private Integer sort;
}

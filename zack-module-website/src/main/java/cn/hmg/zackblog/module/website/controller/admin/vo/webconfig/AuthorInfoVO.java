package cn.hmg.zackblog.module.website.controller.admin.vo.webconfig;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-04 14:29
 * @description: 作者信息vo
 */
@Data
@Schema(name = "作者信息vo")
public class AuthorInfoVO {
    @Schema(description = "id")
    private Long id;

    @Schema(description = "作者名称")
    @NotBlank(message = "作者名称不能为空")
    private String authorName;

    @Schema(description = "作者简介")
    private String authorSummary;

    @Schema(description = "作者头像")
    private String authorAvatar;

    @Schema(description = "关于作者")
    private String aboutAuthor;

    @Schema(description = "邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @Schema(description = "gitee地址")
    private String giteeUrl;

    @Schema(description = "github地址")
    private String githubUrl;
}

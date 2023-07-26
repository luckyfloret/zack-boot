package cn.hmg.zackblog.module.system.controller.admin.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-26 14:45
 * @description: 基础用户VO
 */
@Data
@Schema(name = "基础用户VO")
public class BaseUserVO {
    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "昵称")
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @Schema(description = "用户邮箱")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "无效的邮箱，请输入有效的邮箱")
    private String email;

    @Schema(description = "手机号码")
    @Pattern(regexp = "^[1-9]\\d{10}$", message = "无效的手机号码，请输入11位有效的手机号")
    private String mobile;

    @Schema(description = "用户性别")
    @NotNull(message = "性别不能为空")
    private Integer sex;

    @Schema(description = "用户状态（0 正常、1 停用）")
    @NotNull(message = "状态不能为空")
    private Integer status;
}

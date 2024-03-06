package cn.hmg.zackblog.module.system.controller.admin.user.vo.center;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-21 17:34
 * @description: 更新用户个人信息 request vo
 */
@Data
@Schema(name = "更新用户个人信息 request vo")
public class UserCenterUpdateReqVO {
    @Schema(description = "用户昵称")
    @NotBlank(message = "用户昵称不能为空")
    @Length(max = 20, message = "最大长度为20位")
    private String nickname;
    @Schema(description = "手机号")
    @Pattern(regexp = "^[1-9]\\d{10}$", message = "无效的手机号码，请输入11位有效的手机号")
    private String mobile;
    @Schema(description = "邮箱")
    @Email(message = "无效的邮箱，请输入正确的邮箱")
    private String email;

    @Schema(description = "用户性别")
    private Integer sex;
}

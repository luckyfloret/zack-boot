package cn.hmg.zackblog.module.system.controller.admin.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-26 14:53
 * @description: 更新用户 request vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "更新用户 request vo")
public class UserUpdateReqVO extends BaseUserVO{
    @Schema(description = "用户id")
    @NotNull(message = "用户id不能为空")
    private Long id;
}

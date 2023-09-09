package cn.hmg.zackblog.module.system.controller.admin.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-26 14:53
 * @description: 创建用户 request vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "创建用户 request vo")
public class UserCreateReqVO extends BaseUserVO{
    @Schema(description = "用户类型（1 前台用户， 2 后台用户）")
    @NotNull(message = "用户类型不能为空")
    private Integer type;
}

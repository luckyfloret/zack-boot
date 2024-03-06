package cn.hmg.zackblog.module.system.controller.admin.permission.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-25 0:29
 * @description: 更新角色 request vo
 */
@EqualsAndHashCode(callSuper = true)
@Schema(name = "更新角色 request vo")
@Data
public class RoleUpdateReqVO extends BaseRoleVO{
    @NotNull(message = "角色id不能为空")
    @Schema(description = "id 主键")
    private Long id;
}

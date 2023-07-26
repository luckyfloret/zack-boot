package cn.hmg.zackblog.module.system.controller.admin.permission.vo.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-26 22:46
 * @description: 用户分配角色 request vo
 */
@Data
@Schema(name = "用户分配角色 request vo")
public class PermissionAssignUserRoleReqVO {
    @Schema(description = "用户id")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @Schema(description = "角色id集合")
    private Set<Long> roleIds = Collections.emptySet();
}

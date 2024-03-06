package cn.hmg.zackblog.module.system.controller.admin.permission.vo.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-25 22:58
 * @description: 分配菜单权限 request VO
 */
@Schema(name = "分配菜单权限 request VO")
@Data
public class PermissionMenuAssignReqVO {
    @Schema(description = "角色id")
    @NotNull(message = "角色id不能为空")
    private Long roleId;

    @Schema(description = "菜单id集合")
    private Set<Long> menuIds = Collections.emptySet();
}

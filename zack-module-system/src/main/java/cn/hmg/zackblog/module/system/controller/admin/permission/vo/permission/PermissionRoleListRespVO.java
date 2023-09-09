package cn.hmg.zackblog.module.system.controller.admin.permission.vo.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-26 23:27
 * @description:
 */
@Data
@Schema(name = "角色列表 response vo")
public class PermissionRoleListRespVO {
    @Schema(description = "角色id")
    private Long id;

    @Schema(description = "角色名称")
    private String name;
}

package cn.hmg.zackblog.module.system.controller.admin.permission.vo.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-25 23:18
 * @description: 菜单列表 response VO
 */
@Data
@Schema(name = "菜单列表 response VO", description = "用于分配菜单权限时获取")
public class PermissionMenuListRespVO {
    @Schema(description = "菜单id")
    private Long id;

    @Schema(description = "菜单名称")
    private String name;

    @Schema(description = "父菜单id")
    private Long parentId;
}

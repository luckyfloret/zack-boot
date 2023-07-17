package cn.hmg.zackblog.module.system.controller.admin.permission.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-17 23:16
 * @description: 菜单 response vo
 */
@Schema(name = "菜单 response VO", description = "菜单 response VO")
@Data
public class MenuRespVO {
    @Schema(description = "id 主键")
    private Long id;

    @Schema(description = "菜单名称")
    private String name;

    @Schema(description = "权限标识 or 权限编码哪种叫法都行")
    private String permission;

    @Schema(description = "菜单类型")
    private Integer type;

    @Schema(description = "用户类型（用于分辨菜单导航）")
    private Integer userType;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "父菜单id")
    private Long parentId;

    @Schema(description = "路由地址")
    private String path;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "组件路径")
    private String component;

    @Schema(description = "菜单状态（0 正常、1 停用）")
    private Integer status;

    @Schema(description = "是否可见 （ 1 可见、2 不可见）")
    private Boolean visible;

    @Schema(description = "是否缓存（1 缓存、2 不缓存）")
    private Boolean keepAlive;
}

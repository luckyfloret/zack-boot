package cn.hmg.zackblog.module.system.controller.admin.permission.vo.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-22 23:01
 * @description: 基础菜单VO
 */
@Data
@Schema(name = "基础菜单VO")
public class BaseMenuVO implements Serializable {
    @NotBlank(message = "菜单名称不能为空")
    @Schema(description = "菜单名称")
    private String name;

    @Schema(description = "权限标识 or 权限编码哪种叫法都行")
    private String permission;

    @NotNull(message = "菜单类型不能为空")
    @Schema(description = "菜单类型")
    private Integer type;

    @Schema(description = "排序")
    private Integer sort;

    @NotNull(message = "父菜单id不能为空")
    @Schema(description = "父菜单id")
    private Long parentId;

    @Schema(description = "路由地址")
    private String path;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "组件路径")
    private String component;

    @NotNull(message = "菜单状态不能为空")
    @Schema(description = "菜单状态（0 正常、1 停用）")
    private Integer status;

    @Schema(description = "是否可见 （ 1 可见、2 不可见）")
    private Boolean visible;

    @Schema(description = "是否缓存（1 缓存、2 不缓存）")
    private Boolean keepAlive;
}

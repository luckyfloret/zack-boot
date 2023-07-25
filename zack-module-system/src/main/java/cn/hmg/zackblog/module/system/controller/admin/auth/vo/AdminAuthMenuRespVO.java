package cn.hmg.zackblog.module.system.controller.admin.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-18 10:25
 * @description: 后台菜单 response vo
 */
@Schema(name = "后台菜单 response vo")
@Data
@Builder
public class AdminAuthMenuRespVO {
    @Schema(description = "id 主键")
    private Long id;

    @Schema(description = "菜单名称")
    private String name;

    @Schema(description = "用户类型（用于分辨菜单导航）")
    private Integer userType;

    @Schema(description = "父菜单id")
    private Long parentId;

    @Schema(description = "路由地址")
    private String path;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "组件路径")
    private String component;

    @Schema(description = "是否可见 （ 1 可见、2 不可见）")
    private Boolean visible;

    @Schema(description = "是否缓存（1 缓存、2 不缓存）")
    private Boolean keepAlive;

    /**
     * 子菜单
     */
    private List<AdminAuthMenuRespVO> children;
}

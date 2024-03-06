package cn.hmg.zackblog.module.system.entity.permission;

import cn.hmg.zackblog.framework.mybatisplus.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 后台系统菜单
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@TableName("system_menu")
@Schema(name = "Menu对象", description = "后台系统菜单")
public class Menu extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Long ROOT = 0L;

    @Schema(description = "id 主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "菜单名称")
    private String name;

    @Schema(description = "权限标识 or 权限编码哪种叫法都行")
    private String permission;

    @Schema(description = "菜单类型")
    private Integer type;

    @Schema(description = "用户类型（用于分辨菜单导航）, ps: 扩展字段")
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

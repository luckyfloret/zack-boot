package cn.hmg.zackblog.module.system.entity.permission;

import cn.hmg.zackblog.framework.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 后台系统菜单
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Getter
@Setter
@TableName("system_menu")
@ApiModel(value = "Menu对象", description = "后台系统菜单")
public class Menu extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id 主键")
    private Long id;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("权限标识 or 权限编码哪种叫法都行")
    private String permission;

    @ApiModelProperty("菜单类型")
    private Byte type;

    @ApiModelProperty("用户类型（用于分辨菜单导航）")
    private Byte userType;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("父菜单id")
    private Long parentId;

    @ApiModelProperty("路由地址")
    private String path;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("组件路径")
    private String component;

    @ApiModelProperty("菜单状态（0 正常、1 停用）")
    private Byte status;

    @ApiModelProperty("是否可见 （ 1 可见、2 不可见）")
    private Boolean visible;

    @ApiModelProperty("是否缓存（1 缓存、2 不缓存）")
    private Boolean keepAlive;
}

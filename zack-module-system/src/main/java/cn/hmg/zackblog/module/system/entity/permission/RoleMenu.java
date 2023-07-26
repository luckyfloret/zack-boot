package cn.hmg.zackblog.module.system.entity.permission;

import cn.hmg.zackblog.framework.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 角色和菜单的关联表
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("system_role_menu")
@Schema(name = "RoleMenu对象", description = "角色和菜单的关联表")
public class RoleMenu extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "角色id")
    private Long roleId;

    @Schema(description = "菜单id")
    private Long menuId;
}

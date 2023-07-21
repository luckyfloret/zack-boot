package cn.hmg.zackblog.module.system.entity.permission;

import cn.hmg.zackblog.framework.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 角色管理
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("system_role")
@Schema(name = "Role对象", description = "角色管理")
public class Role extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id 主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色权限字符串")
    private String code;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "数据范围（1 全部数据权限、2 自定义数据权限、3 本部门数据），此字段作为扩展字段")
    private Integer dataScope;

    @Schema(description = "数据范围（指定部门的数组）ps: 扩展字段")
    private String dataScopeDeptIds;

    @Schema(description = "角色状态（0 正常、1 停用）")
    private Integer status;

    @Schema(description = "角色类型")
    private Integer type;

    @Schema(description = "备注")
    private String remark;
}

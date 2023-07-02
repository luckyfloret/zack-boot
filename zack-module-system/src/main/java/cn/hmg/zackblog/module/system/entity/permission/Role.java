package cn.hmg.zackblog.module.system.entity.permission;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 角色管理
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Getter
@Setter
@TableName("system_role")
@ApiModel(value = "Role对象", description = "角色管理")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id 主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("角色权限字符串")
    private String code;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("数据范围（1 全部数据权限、2 自定义数据权限、3 本部门数据），此字段作为扩展字段")
    private Byte dataScope;

    @ApiModelProperty("数据范围（指定部门的数组）ps: 扩展字段")
    private String dataScopeDeptIds;

    @ApiModelProperty("角色状态（0 正常、1 停用）")
    private Byte status;

    @ApiModelProperty("角色类型")
    private Byte type;

    @ApiModelProperty("备注")
    private String remark;
}

package cn.hmg.zackblog.module.system.entity.dict;

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
 * 字典类型
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Getter
@Setter
@TableName("system_dict_type")
@ApiModel(value = "DictType对象", description = "字典类型")
public class DictType implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id (主键）")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("字典名称")
    private String name;

    @ApiModelProperty("字典类型")
    private String type;

    @ApiModelProperty("状态（0正常 1禁用）")
    private Byte status;

    @ApiModelProperty("备注")
    private String remark;
}

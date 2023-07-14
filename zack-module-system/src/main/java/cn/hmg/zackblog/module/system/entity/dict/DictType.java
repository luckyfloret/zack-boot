package cn.hmg.zackblog.module.system.entity.dict;

import cn.hmg.zackblog.framework.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "DictType对象", description = "字典类型")
public class DictType extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id (主键）")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "字典名称")
    private String name;

    @Schema(description = "字典类型")
    private String type;

    @Schema(description = "状态（0正常 1禁用）")
    private Integer status;

    @Schema(description = "备注")
    private String remark;
}

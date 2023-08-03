package cn.hmg.zackblog.module.system.entity.dict;

import cn.hmg.zackblog.framework.mybatisplus.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 数据字典
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("system_dict_data")
@Schema(name = "DictData对象", description = "数据字典")
public class DictData extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id (主键)")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "字典排序")
    private Integer sort;

    @Schema(description = "字典标签")
    private String label;

    @Schema(description = "字典键值")
    private String value;

    @Schema(description = "字典类型")
    private String dictType;

    @Schema(description = "状态 (0正常，1禁用）")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "颜色类型")
    private String colorType;
}

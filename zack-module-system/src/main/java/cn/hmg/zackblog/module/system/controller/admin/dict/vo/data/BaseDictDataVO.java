package cn.hmg.zackblog.module.system.controller.admin.dict.vo.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-01 16:19
 * @description: 基础字典数据vo
 */
@Data
@Schema(description = "基础字典数据vo")
public class BaseDictDataVO {
    @Schema(description = "字典数据排序")
    @NotNull(message = "字典数据排序不能为空")
    private Integer sort;

    @Schema(description = "字典数据标签")
    @NotBlank(message = "字典数据标签不能为空")
    private String label;

    @Schema(description = "字典数据键值")
    @NotBlank(message = "字典数据键值不能为空")
    private String value;

    @Schema(description = "字典数据类型")
    @NotBlank(message = "字典数据类型不能为空")
    private String dictType;

    @Schema(description = "状态 (0正常，1禁用）")
    @NotNull(message = "字典数据状态不能为空")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "颜色类型")
    @NotBlank(message = "颜色类型不能为空")
    private String colorType;
}

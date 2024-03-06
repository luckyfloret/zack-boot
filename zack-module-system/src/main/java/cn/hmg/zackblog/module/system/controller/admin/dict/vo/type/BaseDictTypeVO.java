package cn.hmg.zackblog.module.system.controller.admin.dict.vo.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-31 22:07
 * @description: 基础字典类型vo
 */
@Data
public class BaseDictTypeVO {
    @Schema(description = "字典名称")
    @NotBlank(message = "字典名称不能为空")
    private String name;

    @Schema(description = "字典类型")
    @NotBlank(message = "字典类型不能为空")
    private String type;

    @Schema(description = "状态（0正常 1禁用）")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "备注")
    private String remark;
}

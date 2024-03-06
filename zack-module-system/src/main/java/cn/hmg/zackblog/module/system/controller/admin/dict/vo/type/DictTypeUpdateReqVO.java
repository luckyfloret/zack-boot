package cn.hmg.zackblog.module.system.controller.admin.dict.vo.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-31 22:05
 * @description: 字典类型更新 request vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "字典类型更新 request vo")
public class DictTypeUpdateReqVO extends BaseDictTypeVO{
    @Schema(description = "字典类型id")
    @NotNull(message = "字典类型id不能为空")
    private Long id;
}

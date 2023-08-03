package cn.hmg.zackblog.module.system.controller.admin.dict.vo.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-01 16:19
 * @description: 字典数据更新 request vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "字典数据更新 request vo")
public class DictDataUpdateReqVO extends BaseDictDataVO{
    @Schema(description = "字典数据id")
    @NotNull(message = "字典数据id不能为空")
    private Long id;
}

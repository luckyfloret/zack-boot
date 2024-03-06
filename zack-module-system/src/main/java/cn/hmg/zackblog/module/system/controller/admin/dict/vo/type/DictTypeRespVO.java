package cn.hmg.zackblog.module.system.controller.admin.dict.vo.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-31 22:01
 * @description: 字典类型 response vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "字典类型 response vo")
public class DictTypeRespVO extends BaseDictTypeVO{
    @Schema(description = "字典类型id")
    private Long id;
}

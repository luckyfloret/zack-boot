package cn.hmg.zackblog.module.system.controller.admin.dict.vo.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-01 16:19
 * @description: 字典数据 response vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "字典数据 response vo")
public class DictDataRespVO extends BaseDictDataVO{

    @Schema(description = "字典数据id")
    private Long id;
}

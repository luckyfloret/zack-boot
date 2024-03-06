package cn.hmg.zackblog.module.system.controller.admin.dict.vo.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-11 12:03
 * @description: 字典数据 response vo
 */
@Data
@Schema(name = "字典数据 response vo", description = "字典数据 response vo")
public class DictDataListRespVO {

    @Schema(description = "字典类型")
    private String dictType;

    @Schema(description = "标签")
    private String label;

    @Schema(description = "字典数据值")
    private String value;

    @Schema(description = "颜色类型")
    private String colorType;
}

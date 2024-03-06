package cn.hmg.zackblog.module.system.controller.admin.dict.vo.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-20 15:29
 * @description: 字典类型列表 response vo
 */
@Data
@Schema(name = "字典类型列表 response vo")
public class DictTypeListRespVO {
    @Schema(description = "字典类型id")
    private Long id;
    @Schema(description = "字典类型名称")
    private String name;
    @Schema(description = "字典类型  类型")
    private String type;
}

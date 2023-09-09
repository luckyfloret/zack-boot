package cn.hmg.zackblog.module.system.controller.admin.dict.vo.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-31 22:08
 * @description: 字典类型分页 response vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictTypePageRespVO extends BaseDictTypeVO{
    @Schema(description = "id (主键）")
    private Long id;

    @Schema(description = "创建人")
    private String creator;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}

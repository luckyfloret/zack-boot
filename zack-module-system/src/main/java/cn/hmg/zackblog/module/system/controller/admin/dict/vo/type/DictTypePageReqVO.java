package cn.hmg.zackblog.module.system.controller.admin.dict.vo.type;

import cn.hmg.zackblog.framework.common.pojo.PageQueryParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-31 22:08
 * @description: 字典类型分页 request vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "字典类型分页 request vo")
public class DictTypePageReqVO extends PageQueryParam {
    @Schema(description = "字典类型名称")
    private String name;

    @Schema(description = "字典类型")
    private String type;

    @Schema(description = "字典状态")
    private Integer status;
}

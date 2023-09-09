package cn.hmg.zackblog.module.system.controller.admin.dict.vo.data;

import cn.hmg.zackblog.framework.common.pojo.PageQueryParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-01 16:18
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictDataPageReqVO extends PageQueryParam {
    @Schema(description = "字典数据类型")
    @Size(max = 100, message = "字典类型类型长度不能超过100个字符")
    private String dictType;

    @Schema(description = "状态 (0正常，1禁用）")
    private Integer status;
}

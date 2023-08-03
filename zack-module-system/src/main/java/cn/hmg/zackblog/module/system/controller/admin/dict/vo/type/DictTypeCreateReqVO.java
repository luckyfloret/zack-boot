package cn.hmg.zackblog.module.system.controller.admin.dict.vo.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-31 22:03
 * @description: 字典类型创建 request vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "字典类型创建 request vo")
public class DictTypeCreateReqVO extends BaseDictTypeVO{

}

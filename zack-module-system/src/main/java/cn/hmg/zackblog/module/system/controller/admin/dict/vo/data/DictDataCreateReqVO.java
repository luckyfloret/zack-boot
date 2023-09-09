package cn.hmg.zackblog.module.system.controller.admin.dict.vo.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-01 16:19
 * @description: 字典数据创建 request vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "字典数据创建 request vo")
public class DictDataCreateReqVO extends BaseDictDataVO{

}

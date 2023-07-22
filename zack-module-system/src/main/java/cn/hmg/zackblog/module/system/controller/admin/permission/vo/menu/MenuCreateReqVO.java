package cn.hmg.zackblog.module.system.controller.admin.permission.vo.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-22 22:48
 * @description: 菜单创建VO
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Schema(name = "菜单创建 request VO")
public class MenuCreateReqVO extends BaseMenuVO{

}

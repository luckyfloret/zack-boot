package cn.hmg.zackblog.module.system.controller.admin.permission.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-25 0:07
 * @description: 角色创建request VO
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "角色创建request VO")
public class RoleCreateReqVO extends BaseRoleVO{

}

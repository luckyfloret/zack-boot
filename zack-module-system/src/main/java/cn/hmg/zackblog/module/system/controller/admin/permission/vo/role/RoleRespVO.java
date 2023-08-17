package cn.hmg.zackblog.module.system.controller.admin.permission.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-26 13:51
 * @description: 角色 response vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "角色 response vo")
public class RoleRespVO extends BaseRoleVO{
    @Schema(name = "角色id")
    private Long id;
}

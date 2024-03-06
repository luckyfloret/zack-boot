package cn.hmg.zackblog.module.system.controller.admin.permission.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-24 23:54
 * @description: 角色列表 response vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "角色列表 response vo")
public class RolePageRespVO extends BaseRoleVO{

    @Schema(description = "id 主键")
    private Long id;

    @Schema(description = "角色类型")
    @NotNull(message = "角色类型不能为空")
    private Integer type;

    @Schema(description = "创建时间", required = true, example = "时间戳格式")
    private LocalDateTime createTime;
}

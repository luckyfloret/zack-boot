package cn.hmg.zackblog.module.system.controller.admin.permission.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-26 13:51
 * @description: 角色 response vo
 */
@Data
@Schema(name = "角色 response vo")
public class RoleRespVO {
    @Schema(name = "角色id")
    private Long id;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "角色权限字符串")
    private String code;

    @Schema(description = "角色状态（0 正常、1 停用）")
    private Integer status;


    @Schema(description = "备注")
    private String remark;
}

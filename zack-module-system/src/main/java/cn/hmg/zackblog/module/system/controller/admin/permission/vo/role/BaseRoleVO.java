package cn.hmg.zackblog.module.system.controller.admin.permission.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-24 23:57
 * @description: 基础角色VO
 */
@Data
@Schema(name = "基础角色VO")
public class BaseRoleVO {
    @Schema(description = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String name;

    @Schema(description = "排序")
    @NotNull(message = "排序不能为空")
    private Integer sort;

    @Schema(description = "角色权限字符串")
    @NotBlank(message = "角色编码不能为空")
    private String code;

    @Schema(description = "角色状态（0 正常、1 停用）")
    @NotNull(message = "角色状态不能为空")
    private Integer status;

    @Schema(description = "备注")
    private String remark;
}

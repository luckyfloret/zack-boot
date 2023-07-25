package cn.hmg.zackblog.module.system.controller.admin.permission.vo.role;

import cn.hmg.zackblog.common.pojo.PageQueryParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-24 23:50
 * @description: 角色列表请求参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "角色列表 request VO")
public class RolePageReqVO extends PageQueryParam {
    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色状态")
    private Integer status;
}

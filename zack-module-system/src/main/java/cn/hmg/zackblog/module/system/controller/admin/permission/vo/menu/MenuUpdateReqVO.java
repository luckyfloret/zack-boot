package cn.hmg.zackblog.module.system.controller.admin.permission.vo.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-22 22:54
 * @description: 菜单更新 request VO
 */
@Data
@Builder
@Schema(name = "菜单更新request VO")
public class MenuUpdateReqVO extends BaseMenuVO{
    @Schema(description = "id 主键")
    private Long id;
}

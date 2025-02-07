package cn.hmg.zackblog.module.system.controller.admin.permission.vo.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-17 23:16
 * @description: 菜单 response vo
 */
@EqualsAndHashCode(callSuper = true)
@Schema(name = "菜单 response VO", description = "菜单 response VO")
@Data
public class MenuRespVO extends BaseMenuVO{
    @Schema(description = "id 主键")
    private Long id;

    @Schema(description = "创建时间", required = true, example = "时间戳格式")
    private LocalDateTime createTime;

    @Schema(description = "子菜单")
    private List<MenuRespVO> children;
}

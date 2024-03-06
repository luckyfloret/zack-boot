package cn.hmg.zackblog.module.system.controller.admin.permission.vo.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-17 23:08
 * @description: 菜单列表请求参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "菜单列表 requestVO")
public class MenuListReqVO {

    /**
     * 暂时不支持
     */
//    @Schema(description = "菜单名字")
//    private String name;

    @Schema(description = "状态")
    private Integer status;
}

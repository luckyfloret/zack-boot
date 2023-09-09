package cn.hmg.zackblog.module.website.controller.admin.vo.issues;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-02 21:55
 * @description: 基础Issues vo
 */
@Data
@Schema(name = "基础Issues vo")
public class BaseIssuesVO {
    @Schema(description = "id")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "问题类型（1 需求、2 bug、3 其他）")
    private Integer type;

    @Schema(description = "状态（1 待解决 2 已解决）")
    private Integer status;
}

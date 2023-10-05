package cn.hmg.zackblog.module.website.controller.admin.notice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-01 12:14
 * @description: 通知公告更新状态 request vo
 */
@Data
@Schema(name = "通知公告更新状态 request vo")
public class NoticeUpdateStatusReqVO {
    @NotNull(message = "id不能为空")
    @Schema(description = "id")
    private Long id;

    @NotNull(message = "类型不能为空")
    @Schema(description = "类型")
    private Integer type;

    @NotNull(message = "状态不能为空")
    @Schema(description = "状态")
    private Integer status;
}

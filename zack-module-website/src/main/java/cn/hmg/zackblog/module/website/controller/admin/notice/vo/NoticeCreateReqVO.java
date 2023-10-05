package cn.hmg.zackblog.module.website.controller.admin.notice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-01 11:20
 * @description: 通知公告创建 request vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "通知公告创建 request vo")
public class NoticeCreateReqVO extends BaseNoticeVO{
    @Schema(description = "类型")
    @NotNull(message = "类型不能为空")
    private Integer type;
}

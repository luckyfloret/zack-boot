package cn.hmg.zackblog.module.website.controller.admin.vo.notice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-01 11:21
 * @description: 通知公告更新 request vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "通知公告更新 request vo")
public class NoticeUpdateReqVO extends BaseNoticeVO {
    @Schema(description = "id")
    private Long id;
}

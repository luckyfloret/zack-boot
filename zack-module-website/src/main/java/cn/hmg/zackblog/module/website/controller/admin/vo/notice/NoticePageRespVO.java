package cn.hmg.zackblog.module.website.controller.admin.vo.notice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-01 11:20
 * @description: 通知公告分页 response vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "通知公告分页 response vo")
public class NoticePageRespVO extends  BaseNoticeVO{
    @Schema(description = "id")
    private Long id;

    @Schema(description = "类型")
    private Integer type;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}

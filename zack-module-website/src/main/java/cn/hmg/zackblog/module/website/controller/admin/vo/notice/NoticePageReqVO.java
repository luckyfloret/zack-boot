package cn.hmg.zackblog.module.website.controller.admin.vo.notice;

import cn.hmg.zackblog.framework.common.pojo.PageQueryParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-01 11:20
 * @description: 通知公告分页 request vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "通知公告分页 request vo")
public class NoticePageReqVO extends PageQueryParam {
    @Schema(description = "标题")
    private String title;

    @Schema(description = "类型")
    private Integer type;
}

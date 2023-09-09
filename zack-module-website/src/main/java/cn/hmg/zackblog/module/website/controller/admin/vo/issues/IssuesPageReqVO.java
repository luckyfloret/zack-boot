package cn.hmg.zackblog.module.website.controller.admin.vo.issues;

import cn.hmg.zackblog.framework.common.pojo.PageQueryParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-03 0:03
 * @description: issues 分页 request vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "issues 分页 request vo")
public class IssuesPageReqVO extends PageQueryParam {
    @Schema(description = "标题")
    private String title;

    @Schema(description = "问题类型（1 需求、2 bug、3 其他）")
    private Integer type;

    @Schema(description = "状态（1 待解决 2 已解决）")
    private Integer status;
}

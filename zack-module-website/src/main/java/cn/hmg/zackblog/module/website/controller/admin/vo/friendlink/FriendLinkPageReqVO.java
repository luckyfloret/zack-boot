package cn.hmg.zackblog.module.website.controller.admin.vo.friendlink;

import cn.hmg.zackblog.framework.common.pojo.PageQueryParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-30 14:07
 * @description: 友情链接分页 request vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "友情链接分页 request vo")
public class FriendLinkPageReqVO extends PageQueryParam {
    @Schema(description = "网站名称")
    private String name;

    @Schema(description = "邮箱地址")
    private String email;

    @Schema(description = "审批状态")
    private Integer approvalStatus;
}

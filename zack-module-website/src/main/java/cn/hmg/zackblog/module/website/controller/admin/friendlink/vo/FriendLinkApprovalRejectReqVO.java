package cn.hmg.zackblog.module.website.controller.admin.friendlink.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-30 23:34
 * @description:
 */
@Data
@Schema(name = "审批友情链接")
public class FriendLinkApprovalRejectReqVO {
    @Schema(description = "id")
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "驳回原因")
    private String rejectOpinion;
}

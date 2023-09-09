package cn.hmg.zackblog.module.website.controller.admin.vo.friendlink;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-30 14:06
 * @description: 友情链接分页 response vo
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "友情链接分页 response vo")
public class FriendLinkPageRespVO extends BaseFriendLinkVO{
    @Schema(description = "id")
    private Long id;

    @Schema(description = "审批状态")
    private Integer approvalStatus;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}

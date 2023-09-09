package cn.hmg.zackblog.module.website.controller.admin.vo.friendlink;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-30 14:08
 * @description: 友情链接 response vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "友情链接 response vo")
public class FriendLinkRespVO extends BaseFriendLinkVO{
    @Schema(description = "id")
    private Long id;
}

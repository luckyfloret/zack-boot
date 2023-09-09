package cn.hmg.zackblog.module.website.controller.admin.vo.friendlink;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-30 14:08
 * @description: 友情链接更新 request vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "友情链接更新 request vo")
public class FriendLinkUpdateReqVO extends BaseFriendLinkVO{
    @Schema(description = "id")
    @NotNull(message = "id不能为空")
    private Long id;
}

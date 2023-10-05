package cn.hmg.zackblog.module.website.controller.admin.friendlink.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-30 14:07
 * @description: 友情链接创建 request vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "友情链接创建 request vo")
public class FriendLinkCreateReqVO extends BaseFriendLinkVO{
}

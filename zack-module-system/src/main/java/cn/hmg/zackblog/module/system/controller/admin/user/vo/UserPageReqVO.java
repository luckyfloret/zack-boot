package cn.hmg.zackblog.module.system.controller.admin.user.vo;

import cn.hmg.zackblog.common.pojo.PageQueryParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-26 14:06
 * @description: 用户分页 request vo
 */
@EqualsAndHashCode(callSuper = true)
@Schema(name = "用户分页 request vo")
@Data
public class UserPageReqVO extends PageQueryParam {
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户类型（1 前台用户， 2 后台用户）")
    private Integer type;

    @Schema(description = "用户邮箱")
    private String email;

    @Schema(description = "手机号码")
    private String mobile;

    @Schema(description = "用户状态（0 正常、1 停用）")
    private Integer status;
}

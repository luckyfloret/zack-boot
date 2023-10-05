package cn.hmg.zackblog.module.system.controller.admin.mail.vo.account;

import cn.hmg.zackblog.framework.common.pojo.PageQueryParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-22 19:13
 * @description: 邮箱账户分页 request vo
 */
@Data
@Schema(name = "邮箱账户分页 request vo")
public class MailAccountPageReqVO extends PageQueryParam {
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "邮箱")
    private String email;
}

package cn.hmg.zackblog.module.system.controller.admin.mail.vo.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-22 19:12
 * @description: 邮箱账户分页 response vo
 */
@Data
@Schema(name = "邮箱账户分页 response vo")
public class MailAccountPageRespVO extends BaseMailAccountVO{
    @Schema(description = "id （主键）")
    private Long id;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}

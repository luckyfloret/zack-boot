package cn.hmg.zackblog.module.system.controller.admin.mail.vo.record;

import cn.hmg.zackblog.framework.common.pojo.PageQueryParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-22 19:14
 * @description: 邮件记录分页 request vo
 */
@Data
@Schema(name = "邮件记录分页 request vo")
public class MailRecordPageReqVO extends PageQueryParam {
    @Schema(description = "用户类型（1 前台用户， 2 后台用户）")
    private Integer userType;

    @Schema(description = "邮箱账号id")
    private Long accountId;

    @Schema(description = "模板编码")
    private String templateCode;

    @Schema(description = "发送状态（0 发送失败， 1 发送成功）")
    private Integer sendStatus;
}

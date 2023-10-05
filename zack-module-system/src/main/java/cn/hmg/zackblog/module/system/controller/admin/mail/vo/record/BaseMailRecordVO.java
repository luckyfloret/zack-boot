package cn.hmg.zackblog.module.system.controller.admin.mail.vo.record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-22 19:14
 * @description: 基础邮箱记录 vo
 */
@Data
@Schema(name = "基础邮箱记录 vo")
public class BaseMailRecordVO {
    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "用户类型（1 前台用户， 2 后台用户）")
    private Integer userType;

    @Schema(description = "邮箱账号id")
    private Long accountId;

    @Schema(description = "接收邮箱地址")
    private String toMail;

    @Schema(description = "发送邮箱地址")
    private String fromMail;

    @Schema(description = "模板id")
    private Long templateId;

    @Schema(description = "模板编码")
    private String templateCode;

    @Schema(description = "模板发送人名称")
    private String senderNickname;

    @Schema(description = "邮件模板标题")
    private String templateTitle;

    @Schema(description = "邮件模板内容")
    private String templateContent;

    @Schema(description = "邮件参数")
    private Map<String, Object> templateParams;

    @Schema(description = "发送状态（0 发送失败， 1 发送成功）")
    private Integer sendStatus;

    @Schema(description = "发送时间")
    private LocalDateTime sendTime;

    @Schema(description = "发送异常")
    private String sendException;
}

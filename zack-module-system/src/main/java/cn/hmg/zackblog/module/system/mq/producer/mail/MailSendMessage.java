package cn.hmg.zackblog.module.system.mq.producer.mail;

import cn.hmg.zackblog.framework.rocketmq.core.message.AbstractMessageTemplate;
import cn.hmg.zackblog.module.system.entity.mail.MailAccount;
import cn.hmg.zackblog.module.system.entity.mail.MailTemplate;
import lombok.Data;

import java.util.Map;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-10-03 14:42
 * @description: 邮箱发送消息模板
 */
@Data
public class MailSendMessage extends AbstractMessageTemplate {
    private String message;

    private String templateCode;

    private String content;

    private String toMail;

    private Long userId;

    private Integer userType;

    private MailAccount mailAccount;

    private MailTemplate mailTemplate;

    private Map<String, Object> params;
}

package cn.hmg.zackblog.module.system.mq.producer.mail;

import cn.hmg.zackblog.framework.rocketmq.core.message.AbstractMessageTemplate;
import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-10-03 14:43
 * @description: 邮件模板刷新缓存消息模板
 */
@Data
public class MailTemplateRefreshCacheMessage extends AbstractMessageTemplate {
    private String message;
}

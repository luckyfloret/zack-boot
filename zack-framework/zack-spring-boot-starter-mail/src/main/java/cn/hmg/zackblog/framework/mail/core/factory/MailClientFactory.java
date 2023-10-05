package cn.hmg.zackblog.framework.mail.core.factory;

import cn.hmg.zackblog.framework.mail.core.MailClient;
import cn.hmg.zackblog.framework.mail.core.client.SMTPConfig;
import cn.hmg.zackblog.framework.mail.core.client.SMTPMessageTemplate;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-18 15:10
 * @description: mail 工厂接口
 */
public interface MailClientFactory {
    MailClient getMailClient(String templateCode);

    void createMailClient(String templateCode, SMTPConfig smtpConfig, SMTPMessageTemplate template);

    void clear();
}

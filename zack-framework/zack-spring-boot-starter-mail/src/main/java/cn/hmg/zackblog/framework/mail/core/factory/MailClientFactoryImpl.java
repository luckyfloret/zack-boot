package cn.hmg.zackblog.framework.mail.core.factory;

import cn.hmg.zackblog.framework.mail.core.AbstractMailClient;
import cn.hmg.zackblog.framework.mail.core.MailClient;
import cn.hmg.zackblog.framework.mail.core.client.SMTPClient;
import cn.hmg.zackblog.framework.mail.core.client.SMTPConfig;
import cn.hmg.zackblog.framework.mail.core.client.SMTPMessageTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-18 15:11
 * @description: mail 工厂实现类
 */
@Slf4j
public class MailClientFactoryImpl implements MailClientFactory {

    private final ConcurrentMap<String, AbstractMailClient<SMTPConfig, SMTPMessageTemplate>> clients = new ConcurrentHashMap<>();

    @Override
    public MailClient getMailClient(String templateCode) {
        AbstractMailClient<SMTPConfig, SMTPMessageTemplate> smtpConfigAbstractMailClient = clients.get(templateCode);
        if (Objects.isNull(smtpConfigAbstractMailClient)) {
            log.error("mail客户端不存在，templateCode => {}", templateCode);
        }
        return smtpConfigAbstractMailClient;
    }

    @Override
    public void createMailClient(String templateCode, SMTPConfig smtpConfig, SMTPMessageTemplate template) {
        AbstractMailClient<SMTPConfig, SMTPMessageTemplate> smtpClient = new SMTPClient(templateCode, smtpConfig, template);
        smtpClient.init();
        clients.put(templateCode, smtpClient);
    }

    @Override
    public void clear() {
        clients.clear();
    }
}

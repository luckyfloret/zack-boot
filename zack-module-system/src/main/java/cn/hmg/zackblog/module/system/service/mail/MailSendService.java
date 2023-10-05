package cn.hmg.zackblog.module.system.service.mail;


import cn.hmg.zackblog.module.system.mq.producer.mail.MailSendMessage;

import java.util.Map;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-30 16:20
 * @description: 邮箱发送服务
 */
public interface MailSendService {
    void send(String toMail, String templateCode, Long userId, Integer userType, Map<String, Object> params);

    void doSendMail(MailSendMessage message);
}

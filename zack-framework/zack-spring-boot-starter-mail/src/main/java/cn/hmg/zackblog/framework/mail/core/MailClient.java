package cn.hmg.zackblog.framework.mail.core;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-18 15:05
 * @description:
 */
public interface MailClient {
    void send(String toMail);

    void send(String toMail, String content, boolean isHtml);
}

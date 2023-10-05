package cn.hmg.zackblog.framework.mail.core.client;

import cn.hmg.zackblog.framework.mail.core.AbstractMailClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-18 15:08
 * @description: SMTP客户端
 */
public class SMTPClient extends AbstractMailClient<SMTPConfig, SMTPMessageTemplate> {
    private final JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
    private MimeMessageHelper mimeMessageHelper;
    private MimeMessage mimeMessage;

    public SMTPClient(String templateCode, SMTPConfig smtpConfig, SMTPMessageTemplate messageTemplate) {
        super(templateCode, smtpConfig, messageTemplate);
    }

    @SneakyThrows
    @Override
    public void doInit() {
        //邮箱账户配置
        javaMailSender.setUsername(config.getUsername());
        javaMailSender.setPassword(config.getPassword());
        javaMailSender.setHost(config.getHost());
        javaMailSender.setPort(config.getPort());
        Properties properties = new Properties();
        properties.put("mail.smtp.ssl.enable", String.valueOf(config.isSslEnable()));
        javaMailSender.setJavaMailProperties(properties);

        //邮箱发送模板配置
        mimeMessage = javaMailSender.createMimeMessage();
        mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(config.getEmail(), template.getNickname());
        mimeMessageHelper.setSubject(template.getTitle());
        mimeMessageHelper.setText(template.getContent(), true);
    }

    @SneakyThrows
    @Override
    public void send(String toMail) {
        mimeMessageHelper.setTo(toMail);
        javaMailSender.send(mimeMessage);
    }

    @SneakyThrows
    @Override
    public void send(String toMail, String content, boolean isHtml) {
        mimeMessageHelper.setTo(toMail);
        //重新设置发送时间，避免时间发送时间错误
        mimeMessageHelper.setSentDate(new Date());
        mimeMessageHelper.setText(content, isHtml);
        javaMailSender.send(mimeMessage);
    }
}

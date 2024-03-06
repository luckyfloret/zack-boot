package cn.hmg.zackblog.framework.mail.core.client;

import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-19 8:32
 * @description: SMTP消息模板
 */
@Data
public class SMTPMessageTemplate {
    private String to;

    private String from;

    private String nickname;

    private String title;

    private String content;
}

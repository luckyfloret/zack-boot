package cn.hmg.zackblog.module.system.mq.topic.mail;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-10-03 14:31
 * @description: 邮箱账号topic
 */
public class MailAccountTopic {
    public static final String MAIL_ACCOUNT_REFRESH_CACHE = "system_mail_account_refresh_cache";

    public static final String CONSUMER_GROUP = "mail_account_refresh_consumer_group";

    public static final String TAG = "system_mail_account";
}

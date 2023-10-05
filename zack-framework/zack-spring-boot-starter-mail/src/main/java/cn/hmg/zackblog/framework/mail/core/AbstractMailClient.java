package cn.hmg.zackblog.framework.mail.core;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-18 15:28
 * @description: 抽象邮件客户端模板
 */
@Slf4j
public abstract class AbstractMailClient<AccountConfig, Template> implements MailClient {
    protected AccountConfig config;

    protected Template template;
    private String templateCode;

    public AbstractMailClient(String templateCode, AccountConfig config, Template template) {
        this.templateCode = templateCode;
        this.config = config;
        this.template = template;
    }

    public void init() {
        log.info("{} -> 邮件客户端配置初始化...", this.getClass().getSimpleName());
        doInit();
    }

    protected abstract void doInit();

    public String getTemplateCode() {
        return this.templateCode;
    }
}

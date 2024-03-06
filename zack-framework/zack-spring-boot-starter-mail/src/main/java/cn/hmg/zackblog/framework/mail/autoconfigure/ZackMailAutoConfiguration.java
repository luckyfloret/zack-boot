package cn.hmg.zackblog.framework.mail.autoconfigure;

import cn.hmg.zackblog.framework.mail.core.factory.MailClientFactory;
import cn.hmg.zackblog.framework.mail.core.factory.MailClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-18 14:31
 * @description: mail自动配置类
 */
@AutoConfiguration
public class ZackMailAutoConfiguration {
    @Bean
    public MailClientFactory mailClientFactory() {
        return new MailClientFactoryImpl();
    }
}

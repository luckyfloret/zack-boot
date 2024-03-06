package cn.hmg.zackblog.framework.captcha.aj.autoconfigure;

import cn.hmg.zackblog.framework.captcha.aj.properties.AjCaptchaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@EnableConfigurationProperties(AjCaptchaProperties.class)
@ComponentScan("cn.hmg.zackblog.framework.captcha.aj")
@Import({AjCaptchaServiceAutoConfiguration.class, AjCaptchaStorageAutoConfiguration.class})
public class AjCaptchaAutoConfiguration {
}

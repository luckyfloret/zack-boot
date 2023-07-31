package cn.hmg.zackblog.framework.captcha.autoconfigure;

import cn.hmg.zackblog.framework.captcha.core.service.RedisCaptchaCacheServiceImpl;
import com.anji.captcha.service.CaptchaCacheService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-13 10:58
 * @description: 验证码自动配置类
 */
@AutoConfiguration
@EnableConfigurationProperties(value = CaptchaProperties.class)
public class ZackCaptchaAutoConfiguration {

    @Bean
    public CaptchaCacheService captchaCacheService(StringRedisTemplate stringRedisTemplate){
        return new RedisCaptchaCacheServiceImpl(stringRedisTemplate);
    }
}

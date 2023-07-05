package cn.hmg.zackblog.framework.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-04 23:18
 * @description: security自动配置类
 */
@AutoConfiguration
@EnableConfigurationProperties(value = SecurityProperties.class)
public class ZackSecurityAutoConfiguration {

}

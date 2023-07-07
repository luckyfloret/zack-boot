package cn.hmg.zackblog.framework.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.Resource;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-04 23:18
 * @description: security自动配置类
 */
@AutoConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(value = SecurityProperties.class)
public class ZackSecurityAutoConfiguration {
    @Resource
    private SecurityProperties securityProperties;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                //禁用csrf
                .csrf().disable()
                //开启跨域
                .cors()
                .and()
                //永久关闭session，因为用不到
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers(securityProperties.getPermitAllUrls().toArray(new String[0])).permitAll()
                ;

        return httpSecurity.build();
    }
}

package cn.hmg.zackblog.framework.config;

import cn.hmg.zackblog.framework.core.filter.TokenAuthenticationFilter;
import cn.hmg.zackblog.framework.core.handler.AccessDeniedHandlerImpl;
import cn.hmg.zackblog.framework.core.handler.AuthenticationEntryPointImpl;
import jdk.nashorn.internal.parser.Token;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPointImpl();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(securityProperties, stringRedisTemplate);
    }

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
                //配置授权规则
                .and().authorizeRequests()
                //在yaml里配置的zack.security.permitAllUrls无需认证
                .antMatchers(securityProperties.getPermitAllUrls().toArray(new String[0])).permitAll()
                .antMatchers(HttpMethod.GET, "/*.html", "/**/*.html", "/**/*.js", "/**/*.css",
                        "/*/api-docs", "/swagger-resources").permitAll()
                .anyRequest().authenticated()
                //配置异常处理
                .and().exceptionHandling()
                //权限不足时的处理器
                .accessDeniedHandler(accessDeniedHandler())
                //未认证处理器
                .authenticationEntryPoint(authenticationEntryPoint())
                //添加token过滤器
                .and().addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
        return httpSecurity.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

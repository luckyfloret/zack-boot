package cn.hmg.zackblog.framework.config;

import cn.hmg.zackblog.framework.core.filter.TokenAuthenticationFilter;
import cn.hmg.zackblog.framework.core.handler.AccessDeniedHandlerImpl;
import cn.hmg.zackblog.framework.core.handler.AuthenticationEntryPointImpl;
import cn.hmg.zackblog.framework.core.permission.SecurityPermissionExpression;
import cn.hmg.zackblog.framework.core.permission.SecurityPermissionExpressionImpl;
import cn.hmg.zackblog.framework.core.service.SecurityPermissionService;
import cn.hmg.zackblog.framework.core.service.SecurityUserService;
import cn.hmg.zackblog.framework.core.utils.RedisUtils;
import cn.hmg.zackblog.framework.web.core.handler.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
@EnableConfigurationProperties(value = SecurityProperties.class)
public class ZackSecurityAutoConfiguration {
    @Resource
    private SecurityProperties securityProperties;

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPointImpl();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter(RedisUtils redisUtils, SecurityUserService securityUserService, GlobalExceptionHandler globalExceptionHandler) {
        return new TokenAuthenticationFilter(securityProperties, redisUtils,securityUserService,globalExceptionHandler);
    }

    @Bean("spe")
    public SecurityPermissionExpression securityPermissionExpression(SecurityPermissionService securityPermissionService){
        return new SecurityPermissionExpressionImpl(securityPermissionService);
    }

    /**
     * 注册AuthenticationManager，用户的身份验证交由我接管
     * @param authenticationConfiguration 认证配置
     * @return AuthenticationManager
     * @throws Exception 抛出异常
     */
    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 采用BCryptPasswordEncoder进行密码加密
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

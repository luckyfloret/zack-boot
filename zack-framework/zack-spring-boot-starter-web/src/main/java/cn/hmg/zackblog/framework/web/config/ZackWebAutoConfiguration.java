package cn.hmg.zackblog.framework.web.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-05 10:17
 * @description: web自动配置类
 */
@AutoConfiguration
public class ZackWebAutoConfiguration implements WebMvcConfigurer {
    /**
     * 跨域配置
     * @return CorsFilter
     */
    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //允许所有请求头
        corsConfiguration.addAllowedHeader("*");
        //允许所有请求方式
        corsConfiguration.addAllowedMethod("*");
        //允许所有来源（域名或IP地址)
        corsConfiguration.addAllowedOriginPattern("*");
        //允许发送跨域请求时携带用户凭证（如Cookie）
        corsConfiguration.setAllowCredentials(true);
        //设置预检请求（OPTIONS请求）的最大缓存时间，单位为秒。
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }
}

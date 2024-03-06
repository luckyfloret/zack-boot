package cn.hmg.zackblog.framework.jackson.autoconfigure;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-21 18:22
 * @description: jackson 自动配置类
 */
@AutoConfiguration
public class ZackJacksonAutoConfiguration {
    private final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * LocalDateTime 类型全局时间格式化
     *
     * @return
     */
    @Bean
    public LocalDateTimeSerializer localDateTimeDeserializer() {
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(PATTERN));
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.serializerByType(LocalDateTime.class, localDateTimeDeserializer());
    }
}

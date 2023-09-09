package cn.hmg.zackblog.framework.redis.autoconfigure;

import cn.hmg.zackblog.framework.redis.core.utils.RedisUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-10 15:10
 * @description: redis 自动配置类
 */
@AutoConfiguration
public class ZackRedisAutoConfiguration {

    @ConditionalOnMissingBean(name = "redisTemplate")
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //设置redis连接工厂
        template.setConnectionFactory(redisConnectionFactory);

        //设置key序列化方式为string
        template.setKeySerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());

        //设置value序列化方式为json
        template.setValueSerializer(RedisSerializer.json());
        template.setHashValueSerializer(RedisSerializer.json());
        return template;
    }

    @Bean
    public RedisUtils redisUtils(StringRedisTemplate stringRedisTemplate){
        return new RedisUtils(stringRedisTemplate);
    }
}

package cn.hmg.zackblog.framework.core.utils;

import cn.hmg.zackblog.common.utils.json.JsonUtils;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-16 11:05
 * @description: redis 工具类
 */
@RequiredArgsConstructor
public class RedisUtils {

    private final StringRedisTemplate stringRedisTemplate;


    public <T> void set(String key, T value) {
        stringRedisTemplate.opsForValue().set(key, JsonUtils.toJsonStr(value));
    }

    public <T> void set(final String key, T value, long timeout, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, JsonUtils.toJsonStr(value), timeout, timeUnit);
    }

    public <T> T get(final String key, Class<T> clazz){
        if (exists(key)) {
            return JsonUtils.parseObject(stringRedisTemplate.opsForValue().get(key), clazz);
        }
        return null;
    }


    public String get(final String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void delete(final String key){
        if (exists(key)) {
            stringRedisTemplate.delete(key);
        }
    }

    public Boolean exists(final String key) {
        return stringRedisTemplate.hasKey(key);
    }
}

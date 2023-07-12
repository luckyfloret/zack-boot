package cn.hmg.zackblog.framework.core.definition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-12 0:09
 * @description: redis key 规范
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedisKeyDefinition {
    /**
     * redis key name
     */
    private String key;

    /**
     * 值的类型
     */
    private Class<?> valueType;

    /**
     * 过期时间， 以秒为单位
     */
    private long expireTime;

    /**
     * 时间类型
     */
    private TimeUnit timeType;

    /**
     * 备注
     */
    private String remark;


    /**
     * 格式化 redis key
     * @param val 格式化的参数
     * @return 格式化后的key
     */
    public String format(Object... val){
        return String.format(this.key, val);
    }
}

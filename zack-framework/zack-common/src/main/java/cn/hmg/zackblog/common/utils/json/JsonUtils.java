package cn.hmg.zackblog.common.utils.json;

import cn.hutool.json.JSONUtil;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-16 12:24
 * @description: JSON 工具类
 */
public class JsonUtils {
    /**
     * 把json字符串转换为指定的类型
     * @param jsonStr json字符串
     * @param clazz 类型
     * @return 解析后的类型
     * @param <T> 泛型方法
     */
    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        if (isJson(jsonStr)) {
            return JSONUtil.toBean(jsonStr, clazz);
        }
        return null;
    }

    /**
     * 转换为json字符串
     * @param obj 对象
     * @return json字符串
     * @param <T> 泛型方法
     */
    public static <T> String toJsonStr(T obj){
        return JSONUtil.toJsonStr(obj);
    }

    /**
     * content是否是json字符串
     * @param content 字符串
     * @return true or false
     */
    public static boolean isJson(String content) {
        return JSONUtil.isTypeJSON(content);
    }
}

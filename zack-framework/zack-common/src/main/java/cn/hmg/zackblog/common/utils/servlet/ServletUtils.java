package cn.hmg.zackblog.common.utils.servlet;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-08 16:49
 * @description: servlet 工具类
 */
public class ServletUtils {
    /**
     *  返回json字符串
     * @param response htpp响应
     * @param obj 内容
     */
    public static void writeJson(HttpServletResponse response, Object obj) {
        String content = JSONUtil.toJsonStr(obj);
        ServletUtil.write(response, content, MediaType.APPLICATION_JSON_UTF8_VALUE);
    }
}

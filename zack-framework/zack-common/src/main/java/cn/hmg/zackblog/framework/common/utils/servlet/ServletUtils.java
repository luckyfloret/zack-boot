package cn.hmg.zackblog.framework.common.utils.servlet;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-08 16:49
 * @description: servlet 工具类
 */
public class ServletUtils {
    /**
     * 返回json字符串
     *
     * @param response http响应
     * @param obj      内容
     */
    public static void writeJson(HttpServletResponse response, Object obj) {
        String content = JSONUtil.toJsonStr(obj);
        ServletUtil.write(response, content, "application/json;charset=UTF-8");
    }


    /**
     * 获取浏览器ua
     * @return ua
     */
    public static String getUserAgent() {
        HttpServletRequest request = getRequest();
        return request.getHeader("User-Agent");
    }

    /**
     * 获取客户端IP
     * @return client IP
     */
    public static String getClientIp() {
        HttpServletRequest request = getRequest();
        return ServletUtil.getClientIP(request);
    }


    /**
     * 获取请求
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        return requestAttributes.getRequest();
    }
}

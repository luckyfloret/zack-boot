package cn.hmg.zackblog.framework.web.core.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Objects;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-25 12:58
 * @description: web 工具类
 */
public class WebFrameworkUtils {

    /**
     * 通过RequestContextHolder获取HandlerMethod
     * @return HandlerMethod
     */
    public static HandlerMethod getHandlerMethod(){
        return (HandlerMethod) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).getAttribute(
                HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
    }
}

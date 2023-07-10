package cn.hmg.zackblog.framework.core.utils;

import cn.hmg.zackblog.framework.core.pojo.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-09 23:33
 * @description: spring security 工具类
 */
@RequiredArgsConstructor
public class SecurityUtils {


    private StringRedisTemplate stringRedisTemplate;

    private static final String AUTHENTICATION_BEARER = "Bearer";

    /**
     * 获取token
     * @param request 请求域
     * @param header 请求头名
     * @return token
     */
    public static String getToken(HttpServletRequest request, String header){
        String token = request.getHeader(header);
        if (!StringUtils.hasText(token)) {
            return null;
        }

        int index = token.indexOf(AUTHENTICATION_BEARER);
        if (-1 == index) {
            return null;
        }
        return token.substring(index + 7).trim();
    }


    public static void setLoginUser(){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(null, null, null);
        usernamePasswordAuthenticationToken.setDetails(null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
    public static LoginUser getLoginUser(){
        return null;
    }
}

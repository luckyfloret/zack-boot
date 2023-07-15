package cn.hmg.zackblog.framework.core.utils;

import cn.hmg.zackblog.common.enums.UserTypeEnum;
import cn.hmg.zackblog.framework.core.pojo.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Security;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-09 23:33
 * @description: spring security 工具类
 */
public class SecurityUtils {
    private static final Map<Integer, UserTypeEnum> USER_TYPE_MAPS = new HashMap<>();
    private static final String AUTHENTICATION_BEARER = "Bearer";

    static {
        USER_TYPE_MAPS.put(UserTypeEnum.FRONT_USER.getType(), UserTypeEnum.FRONT_USER);
        USER_TYPE_MAPS.put(UserTypeEnum.ADMIN_USER.getType(), UserTypeEnum.ADMIN_USER);
    }

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

        return token;
//        int index = token.indexOf(AUTHENTICATION_BEARER);
//        if (-1 == index) {
//            return null;
//        }
//        return token.substring(index + 7).trim();
    }

    public static UserTypeEnum getUserType(Integer userType){
        return USER_TYPE_MAPS.get(userType);
    }

    /**
     * 设置用户信息到Security上下文中
     * @param loginUser 用户信息
     * @param request 请求
     */
    public static void setLoginUser(LoginUser loginUser, HttpServletRequest request){
        Authentication authentication = buildAuthentication(loginUser, request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * 构建认证
     * @param loginUser 用户信息
     * @param request 请求
     * @return Authentication
     */
    public static Authentication buildAuthentication(LoginUser loginUser, HttpServletRequest request){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, Collections.emptyList());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return usernamePasswordAuthenticationToken;
    }

    /**
     * 获取用户信息
     * @return LoginUser
     */
    public static LoginUser getLoginUser(){
        Authentication authentication = getAuthentication();
        if (Objects.isNull(authentication)) {
            return null;
        }
        return authentication.getPrincipal() instanceof LoginUser ? (LoginUser) authentication.getPrincipal() : null;
    }

    /**
     *  获取Authentication
     * @return Authentication
     */
    public static Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Long getLoginUserId(){
        LoginUser loginUser = getLoginUser();
        if (Objects.isNull(loginUser)) {
            return null;
        }
        return loginUser.getUserId();
    }
}

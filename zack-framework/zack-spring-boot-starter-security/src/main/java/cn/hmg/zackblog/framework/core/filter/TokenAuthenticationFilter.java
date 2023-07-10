package cn.hmg.zackblog.framework.core.filter;

import cn.hmg.zackblog.framework.config.SecurityProperties;
import cn.hmg.zackblog.framework.core.pojo.LoginUser;
import cn.hmg.zackblog.framework.core.utils.SecurityUtils;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-08 16:39
 * @description: token认证过滤器
 */
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final SecurityProperties securityProperties;

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = SecurityUtils.getToken(request, securityProperties.getHeader());
        if (StrUtil.isNotEmpty(token)) {
            //校验token
            LoginUser loginUser = verityAccessToken(token);
            //校验用户类型，前台用户 or 后台用户

            //设置security 上下文
        }

        //继续过滤链
        filterChain.doFilter(request, response);
    }

    private LoginUser verityAccessToken(String token) {

        return null;
    }
}

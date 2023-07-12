package cn.hmg.zackblog.framework.core.filter;

import cn.hmg.zackblog.common.enums.UserTypeEnum;
import cn.hmg.zackblog.common.exception.ServiceException;
import cn.hmg.zackblog.common.exception.enums.GlobalErrorCode;
import cn.hmg.zackblog.common.utils.date.DateUtils;
import cn.hmg.zackblog.framework.config.SecurityProperties;
import cn.hmg.zackblog.framework.core.constants.RedisKeyConstant;
import cn.hmg.zackblog.framework.core.pojo.LoginUser;
import cn.hmg.zackblog.framework.core.utils.SecurityUtils;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

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
            verifyUserType(loginUser.getUserType());
            //设置security 上下文
            SecurityUtils.setLoginUser(loginUser, request);
        }

        //继续过滤链
        filterChain.doFilter(request, response);
    }

    private LoginUser verityAccessToken(String accessToken) {
        //校验token是否存在
        String formatAccessToken = RedisKeyConstant.ACCESS_TOKEN.format(accessToken);
        LoginUser loginUser = JSONUtil.toBean(stringRedisTemplate.opsForValue().get(formatAccessToken), LoginUser.class);

        if (Objects.isNull(loginUser)) {
            throw new ServiceException(GlobalErrorCode.UNAUTHORIZED.getCode(), GlobalErrorCode.UNAUTHORIZED.getMessage());
        }

        //校验token是否过期
        if (DateUtils.isExpire(loginUser.getAccessTokenExpireTime())) {
            throw new ServiceException(GlobalErrorCode.UNAUTHORIZED.getCode(), GlobalErrorCode.UNAUTHORIZED.getMessage());
        }
        return loginUser;
    }

    private void verifyUserType(Integer userType) {
        UserTypeEnum userTypeEnum = SecurityUtils.getUserType(userType);
        if (Objects.isNull(userTypeEnum)) {
            throw new AccessDeniedException("错误的用户类型");
        }
    }
}

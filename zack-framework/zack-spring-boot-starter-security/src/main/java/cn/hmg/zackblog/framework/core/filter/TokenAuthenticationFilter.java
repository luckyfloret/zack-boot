package cn.hmg.zackblog.framework.core.filter;

import cn.hmg.zackblog.common.enums.CommonStatusEnum;
import cn.hmg.zackblog.common.enums.UserTypeEnum;
import cn.hmg.zackblog.common.exception.ServiceException;
import cn.hmg.zackblog.common.exception.enums.GlobalErrorCode;
import cn.hmg.zackblog.common.utils.date.DateUtils;
import cn.hmg.zackblog.framework.config.SecurityProperties;
import cn.hmg.zackblog.framework.core.constants.RedisKeyConstant;
import cn.hmg.zackblog.framework.core.pojo.LoginUser;
import cn.hmg.zackblog.framework.core.utils.SecurityUtils;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.NonNull;
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
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = SecurityUtils.getToken(request, securityProperties.getHeader());
        if (StrUtil.isNotEmpty(token)) {
            //校验token
            LoginUser loginUser = verityAccessToken(token);

            //校验用户类型，前台用户 or 后台用户
            verifyUserType(loginUser.getUserType());

            //检查用户账号状态是否被禁用
//            checkUserStatus(loginUser.getUserId());

            //设置security 上下文
            SecurityUtils.setLoginUser(loginUser, request);
        }

        //继续过滤链
        filterChain.doFilter(request, response);
    }

    /**
     * 校验用户账号状态
     * @param userId 用户id
     */
//    private void checkUserStatus(Long userId) {
//        User user = userService.getById(userId);
//        if (Objects.isNull(user)) {
//            throw new ServiceException(GlobalErrorCode.UNAUTHORIZED.getCode(), GlobalErrorCode.UNAUTHORIZED.getMessage());
//        }
//
//        //校验用户账号状态
//        if (!ObjUtil.notEqual(user.getStatus(), CommonStatusEnum.DISABLED.getStatusCode())) {
//            throw new ServiceException(ErrorCodeEnum.AUTH_USER_DISABLED.getCode(), ErrorCodeEnum.AUTH_USER_DISABLED.getMessage());
//        }
//    }

    /**
     * 校验访问令牌
     * @param accessToken 访问令牌
     * @return LoginUser
     */
    private LoginUser verityAccessToken(String accessToken) {
        //校验token是否存在
        String formatAccessToken = RedisKeyConstant.TOKEN_INFO.format(accessToken);
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

    /**
     *  校验用户类型
     * @param userType 用户类型
     */
    private void verifyUserType(Integer userType) {
        UserTypeEnum userTypeEnum = SecurityUtils.getUserType(userType);
        if (Objects.isNull(userTypeEnum)) {
            throw new AccessDeniedException("错误的用户类型");
        }
    }
}

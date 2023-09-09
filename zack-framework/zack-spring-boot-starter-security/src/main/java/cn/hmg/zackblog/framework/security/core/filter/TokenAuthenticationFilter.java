package cn.hmg.zackblog.framework.security.core.filter;

import cn.hmg.zackblog.framework.common.enums.CommonStatusEnum;
import cn.hmg.zackblog.framework.common.enums.UserTypeEnum;
import cn.hmg.zackblog.framework.common.exception.BusinessException;
import cn.hmg.zackblog.framework.common.exception.enums.GlobalErrorCode;
import cn.hmg.zackblog.framework.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.common.utils.date.DateUtils;
import cn.hmg.zackblog.framework.common.utils.servlet.ServletUtils;
import cn.hmg.zackblog.framework.security.autoconfigure.SecurityProperties;
import cn.hmg.zackblog.framework.security.core.constants.RedisKeyConstant;
import cn.hmg.zackblog.framework.security.core.pojo.LoginUser;
import cn.hmg.zackblog.framework.security.core.pojo.UserDetails;
import cn.hmg.zackblog.framework.security.core.service.SecurityUserService;
import cn.hmg.zackblog.framework.redis.core.utils.RedisUtils;
import cn.hmg.zackblog.framework.security.core.utils.SecurityUtils;
import cn.hmg.zackblog.framework.web.core.handler.GlobalExceptionHandler;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final SecurityProperties securityProperties;

    private final RedisUtils redisUtils;

    private final SecurityUserService securityUserService;

    private final GlobalExceptionHandler globalExceptionHandler;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return securityProperties.getPermitAllUrls().contains(request.getRequestURI());
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = SecurityUtils.getToken(request, securityProperties.getHeader());
        if (StrUtil.isNotEmpty(token)) {
            try {
                //校验token
                LoginUser loginUser = verityAccessToken(token);

                //校验用户类型，前台用户 or 后台用户
                verifyUserType(loginUser.getUserType(), request.getRequestURI());

                //检查用户账号状态是否被禁用
                checkUserStatus(loginUser.getUserId());

                //设置security 上下文
                SecurityUtils.setLoginUser(loginUser, request);
            } catch (BusinessException e) {
                log.error("exception => code => {}，message => {}", e.getCode(), e.getMessage());
                CommonResult<?> commonResult = globalExceptionHandler.serviceException(e);
                ServletUtils.writeJson(response, commonResult);
                return;
            }
        }

        //继续过滤链
        filterChain.doFilter(request, response);
    }

    /**
     * 校验用户账号状态
     *
     * @param userId 用户id
     */
    private void checkUserStatus(Long userId) {
        UserDetails user = securityUserService.getUserDetailsByUserId(userId);

        //校验用户账号状态
        if (!ObjUtil.notEqual(user.getStatus(), CommonStatusEnum.DISABLED.getStatusCode())) {
            throw new BusinessException(10200001, "账号已被禁用，请联系管理员");
        }
    }

    /**
     * 校验访问令牌
     *
     * @param accessToken 访问令牌
     * @return LoginUser
     */
    private LoginUser verityAccessToken(String accessToken) {
        //校验token是否存在
        String formatAccessToken = RedisKeyConstant.ACCESS_TOKEN.format(accessToken);
        LoginUser loginUser = redisUtils.get(formatAccessToken, LoginUser.class);

        if (Objects.isNull(loginUser)) {
            throw new BusinessException(GlobalErrorCode.UNAUTHORIZED.getCode(), GlobalErrorCode.UNAUTHORIZED.getMessage());
        }

        //校验token是否过期
        if (DateUtils.isExpire(loginUser.getAccessTokenExpireTime())) {
            throw new BusinessException(GlobalErrorCode.UNAUTHORIZED.getCode(), GlobalErrorCode.UNAUTHORIZED.getMessage());
        }
        return loginUser;
    }

    /**
     * 校验用户类型
     *
     * @param userType 用户类型
     */
    private void verifyUserType(Integer userType, String requestUri) {
        UserTypeEnum userTypeEnum = SecurityUtils.getUserType(userType);
        if (Objects.isNull(userTypeEnum)) {
            throw new AccessDeniedException("错误的用户类型");
        }

        //校验访问url，禁止前台用户访问后台接口，暂时通过访问前缀限制实现
//        String prefixUri = "/admin";
//        if (Objects.equals(userType, UserTypeEnum.FRONT_USER.getType()) && prefixUri.startsWith(requestUri)) {
//            throw new AccessDeniedException("未授权");
//        }
    }
}

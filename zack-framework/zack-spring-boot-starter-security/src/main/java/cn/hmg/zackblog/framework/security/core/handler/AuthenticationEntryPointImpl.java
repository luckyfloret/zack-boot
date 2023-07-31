package cn.hmg.zackblog.framework.security.core.handler;

import cn.hmg.zackblog.framework.common.exception.enums.GlobalErrorCode;
import cn.hmg.zackblog.framework.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.common.utils.servlet.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-08 16:42
 * @description: 认证未通过处理器
 */
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("用户未认证");
        response.setStatus(GlobalErrorCode.UNAUTHORIZED.getCode());
        ServletUtils.writeJson(response, CommonResult.error(GlobalErrorCode.UNAUTHORIZED.getCode(), GlobalErrorCode.UNAUTHORIZED.getMessage()));
    }
}

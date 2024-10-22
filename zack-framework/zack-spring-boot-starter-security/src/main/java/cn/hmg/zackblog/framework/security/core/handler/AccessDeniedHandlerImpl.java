package cn.hmg.zackblog.framework.security.core.handler;

import cn.hmg.zackblog.framework.common.exception.enums.GlobalErrorCode;
import cn.hmg.zackblog.framework.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.common.utils.servlet.ServletUtils;
import cn.hmg.zackblog.framework.security.core.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-08 16:46
 * @description: 授权失败处理器
 */
@Slf4j
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("用户权限不足，用户id => {}", SecurityUtils.getLoginUserId());
        response.setStatus(GlobalErrorCode.FORBIDDEN.getCode());
        ServletUtils.writeJson(response, CommonResult.error(GlobalErrorCode.FORBIDDEN.getCode(), GlobalErrorCode.FORBIDDEN.getMessage()));
    }
}

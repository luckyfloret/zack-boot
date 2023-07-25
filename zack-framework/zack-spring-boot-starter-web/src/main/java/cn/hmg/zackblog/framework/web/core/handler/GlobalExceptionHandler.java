package cn.hmg.zackblog.framework.web.core.handler;

import cn.hmg.zackblog.common.exception.ServerException;
import cn.hmg.zackblog.common.exception.ServiceException;
import cn.hmg.zackblog.common.exception.enums.GlobalErrorCode;
import cn.hmg.zackblog.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.web.core.utils.WebFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-08 22:55
 * @description: 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 系统内部异常，兜底规则
     *
     * @return CommonResult
     */
    @ExceptionHandler(ServerException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult<?> serverException(ServerException ex) {
        log.error("serverException => ", ex);
        return CommonResult.error(GlobalErrorCode.SERVER_ERROR.getCode(), GlobalErrorCode.SERVER_ERROR.getMessage());
    }

    /**
     * 业务异常，通用异常处理
     *
     * @param ex 业务异常
     * @return CommonResult
     */
    @ExceptionHandler(ServiceException.class)
    public CommonResult<?> serviceException(ServiceException ex) {
        log.error("serviceException => ", ex);
        return CommonResult.error(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<?> methodArgumentNotValidExceptionException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getFieldError();
        MethodParameter parameter = ex.getParameter();
        assert fieldError != null;
        log.error("[methodArgumentNotValidExceptionException => 参数校验异常] ===> [{className：{}} =====> {methodName：{}} =====> {requestParameter：{}} =====> {field：{}} =====> {errorMessage：{}}]",
                parameter.getDeclaringClass().getSimpleName(),
                Objects.requireNonNull(parameter.getMethod()).getName(), ex.getObjectName(),
                fieldError.getField(), fieldError.getDefaultMessage());
        return CommonResult.error(HttpServletResponse.SC_BAD_REQUEST, fieldError.getDefaultMessage());
    }

    @ExceptionHandler(BindException.class)
    public CommonResult<?> bindException(BindException ex) {
        log.error("[bindException => 参数校验异常] ===> [{className：{}} =====> {methodName：{}} =====> {requestParameter：{}} =====> {field：{}} =====> {errorMessage：{}}]",
                getClassName(), getMethodName() ,ex.getObjectName(),
                Objects.requireNonNull(ex.getFieldError()).getField(), ex.getFieldError().getDefaultMessage());
        return CommonResult.error(HttpStatus.BAD_REQUEST.value(), ex.getFieldError().getDefaultMessage());
    }

    private String getClassName(){
        return WebFrameworkUtils.getHandlerMethod().getBeanType().getSimpleName();
    }

    private String getMethodName(){
        return WebFrameworkUtils.getHandlerMethod().getMethod().getName();
    }
}

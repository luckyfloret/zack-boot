package cn.hmg.zackblog.framework.web.core.handler;

import cn.hmg.zackblog.common.exception.ServerException;
import cn.hmg.zackblog.common.exception.ServiceException;
import cn.hmg.zackblog.common.exception.enums.GlobalErrorCode;
import cn.hmg.zackblog.common.pojo.CommonResult;
import cn.hmg.zackblog.framework.web.core.utils.WebFrameworkUtils;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import static cn.hmg.zackblog.common.exception.enums.GlobalErrorCode.*;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-08 22:55
 * @description: 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String LOG_ERROR_MESSAGE_TEMPLATE = "[{className：{}} =====> {methodName：{}} =====> {requestParameter：{}} =====> {field：{}} =====> {errorMessage：{}}]";

    /**
     * 系统内部异常，兜底规则
     *
     * @return CommonResult
     */
    @ExceptionHandler(ServerException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult<?> serverException(ServerException ex) {
        log.error("serverException => ", ex);
        return CommonResult.error(SERVER_ERROR.getCode(), SERVER_ERROR.getMessage());
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
        log.error(String.format("[methodArgumentNotValidExceptionException => 参数校验异常] ===> %s", LOG_ERROR_MESSAGE_TEMPLATE),
                parameter.getDeclaringClass().getSimpleName(),
                Objects.requireNonNull(parameter.getMethod()).getName(), ex.getObjectName(),
                fieldError.getField(), fieldError.getDefaultMessage());
        return CommonResult.error(BAD_REQUEST.getCode(), fieldError.getDefaultMessage());
    }

    @ExceptionHandler(BindException.class)
    public CommonResult<?> bindException(BindException ex) {
        log.error(String.format("[bindException => 参数校验异常] ===> %s", LOG_ERROR_MESSAGE_TEMPLATE),
                getClassName(), getMethodName(), ex.getObjectName(),
                Objects.requireNonNull(ex.getFieldError()).getField(), ex.getFieldError().getDefaultMessage());
        return CommonResult.error(BAD_REQUEST.getCode(), ex.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CommonResult<?> httpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error(String.format("[httpMessageNotReadableException => 消息不可读异常] ===> %s", LOG_ERROR_MESSAGE_TEMPLATE),
                    getClassName(), getMethodName(),"", "" , ex.getMessage());
        return CommonResult.error(BAD_REQUEST.getCode(), BAD_REQUEST.getMessage());
    }

    private String getClassName() {
        return WebFrameworkUtils.getHandlerMethod().getBeanType().getSimpleName();
    }

    private String getMethodName() {
        return WebFrameworkUtils.getHandlerMethod().getMethod().getName();
    }
}

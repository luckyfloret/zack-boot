package cn.hmg.zackblog.framework.web.core.handler;

import cn.hmg.zackblog.common.exception.ServerException;
import cn.hmg.zackblog.common.exception.ServiceException;
import cn.hmg.zackblog.common.exception.enums.GlobalErrorCode;
import cn.hmg.zackblog.common.pojo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


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
     * @param ex  业务异常
     * @return CommonResult
     */
    @ExceptionHandler(ServiceException.class)
    public CommonResult<?> serviceException(ServiceException ex) {
        log.error("serviceException => ", ex);
        return CommonResult.error(ex.getCode(), ex.getMessage());
    }
}

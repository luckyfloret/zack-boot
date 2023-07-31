package cn.hmg.zackblog.framework.common.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

import static cn.hmg.zackblog.framework.common.exception.enums.GlobalErrorCode.*;
import static cn.hmg.zackblog.framework.common.enums.GlobalSuccessCodeEnum.*;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-06 10:18
 * @description: 封装统一返回
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CommonResult<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;

    public CommonResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonResult(Integer code, String message, T data) {
        this(code, message);
        this.data = data;
    }

    public static <T> CommonResult<T> success() {
        return new CommonResult<>(SUCCESS.getCode(), SUCCESS.getMessage());
    }

    public static <T> CommonResult<T> success(Integer code, String message, T data) {
        return new CommonResult<>(code, message, data);
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(SUCCESS.getCode(), SUCCESS.getMessage(), data);
    }

    public static <T> CommonResult<T> error() {
        return new CommonResult<>(SERVER_ERROR.getCode(), SERVER_ERROR.getMessage());
    }

    public static <T> CommonResult<T> error(Integer code, String message, T data) {
        return new CommonResult<>(code, message, data);
    }

    public static <T> CommonResult<T> error(Integer code, String message) {
        return new CommonResult<>(code, message);
    }

}

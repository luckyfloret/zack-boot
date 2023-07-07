package cn.hmg.zackblog.common.pojo;

import cn.hmg.zackblog.common.exception.enums.GlobalErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

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

    public CommonResult(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public CommonResult(Integer code, String message, T data){
        this(code, message);
        this.data = data;
    }

    public static <T> CommonResult<T> success(){
        return new CommonResult<>(200, "success");
    }

    public static <T> CommonResult<T> success(Integer code, String message, T data){
        return new CommonResult<>(code, message, data);
    }

    public static <T> CommonResult<T> success(T data){
        return new CommonResult<>(200, "success", data);
    }

    public static <T> CommonResult<T> error(){
        return new CommonResult<>(GlobalErrorCode.SERVER_ERROR.getCode(), GlobalErrorCode.SERVER_ERROR.getMessage());
    }

    public static <T> CommonResult<T> error(Integer code, String message, T data){
        return new CommonResult<>(code, message, data);
    }

    public static <T> CommonResult<T> error(Integer code, String message){
        return new CommonResult<>(code, message);
    }

}

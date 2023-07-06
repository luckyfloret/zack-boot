package cn.hmg.zackblog.common.pojo;

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

    public CommonResult(){

    }
}

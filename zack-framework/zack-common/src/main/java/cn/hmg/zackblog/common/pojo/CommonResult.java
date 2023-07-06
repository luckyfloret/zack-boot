package cn.hmg.zackblog.common.pojo;

import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-06 10:18
 * @description: 封装统一返回
 */
@Data
public class CommonResult<T> {
    private Integer code;
    private String message;
    private T data;


}

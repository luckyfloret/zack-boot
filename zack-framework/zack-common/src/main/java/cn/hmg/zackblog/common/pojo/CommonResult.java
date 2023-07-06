package cn.hmg.zackblog.common.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-06 10:18
 * @description: 封装统一返回
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CommonResult<T> implements Serializable {
}

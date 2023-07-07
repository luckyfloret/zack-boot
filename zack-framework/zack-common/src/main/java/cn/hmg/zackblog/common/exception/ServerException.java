package cn.hmg.zackblog.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-07 9:54
 * @description: 服务器异常
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ServerException extends RuntimeException{
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 异常信息
     */
    private String message;

    public ServerException(){}

    public ServerException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

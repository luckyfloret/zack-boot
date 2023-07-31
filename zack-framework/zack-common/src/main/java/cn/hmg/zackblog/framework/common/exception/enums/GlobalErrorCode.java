package cn.hmg.zackblog.framework.common.exception.enums;

import lombok.Getter;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-07 9:47
 * @description:  全局异常信息枚举，只提供系统级别和一些全局通用的异常，如需具体的业务异常信息请在业务里声明
 */
@Getter
public enum GlobalErrorCode {

    /**
     * 系统级异常
     */
    SERVER_ERROR(500, "系统异常，请稍后再试~"),


    /**
     * 全局通用异常
     */
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "请登录再试"),
    FORBIDDEN(403, "没有权限，请联系管理员"),
    NOT_FOUND(404, "找不到请求"),
    ;

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 异常信息
     */
    private final String message;

    GlobalErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

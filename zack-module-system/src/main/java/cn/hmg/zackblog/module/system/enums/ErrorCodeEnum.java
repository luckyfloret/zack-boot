package cn.hmg.zackblog.module.system.enums;

import lombok.Getter;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-14 14:05
 * @description: 系统管理模块业务错误枚举，业务错误码范围：1-02-00-000
 */
@Getter
public enum ErrorCodeEnum {

    /**
     * ############################ Auth模块 10200000 ############################
     */
    AUTH_BAD_CREDENTIALS(10200000, "账号或密码错误"),
    AUTH_USER_DISABLED(10200001, "账号已被禁用，请联系管理员"),
    AUTH_CAPTCHA_ERROR(10200002, "验证码错误"),
    AUTH_USER_ILLEGAL_LOGIN(10200003, "非法登录"),

    /**
     * ############################ User模块 10201000 ############################
     */
    USER_USERNAME_EXISTS(10201000,"用户账号已存在"),;



    ErrorCodeEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
    private final Integer code;
    private final String message;
}

package cn.hmg.zackblog.module.system.enums;

import lombok.Getter;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-13 23:05
 * @description: 登录结果枚举
 */
@Getter
public enum LoginResultEnum {

    /**
     * 登录成功结果码为200，  登录失败结果码范围是10-50
     */
    SUCCESS(200, "登录成功"),
    BAD_CREDENTIALS(10, "账号或密码不正确"),
    USER_DISABLED(11, "账号被禁用"),
    CAPTCHA_ERROR(12, "验证码错误"),
    USER_ILLEGAL_LOGIN(13, "非法登录"),
    ;


    LoginResultEnum(Integer result, String description) {
        this.result = result;
        this.description = description;
    }

    /**
     * 结果码
     */
    private final Integer result;

    /**
     * 描述
     */
    private final String description;
}

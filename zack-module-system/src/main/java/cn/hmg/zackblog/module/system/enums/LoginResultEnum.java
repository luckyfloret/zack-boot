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
     *
     */
    SUCCESS(200, "登录成功"),;

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

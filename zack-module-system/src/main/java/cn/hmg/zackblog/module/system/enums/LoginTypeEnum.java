package cn.hmg.zackblog.module.system.enums;

import lombok.Getter;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-13 22:48
 * @description: 登录类型枚举
 */
@Getter
public enum LoginTypeEnum {

    /**
     * 登录类型范围 100 - 150
     */
    LOGIN_USERNAME(100, "账户密码登录"),;


    LoginTypeEnum(Integer type, String description){
        this.type = type;
        this.description = description;
    }

    /**
     * 登录类型
     */
    private final Integer type;

    /**
     * 描述
     */
    private final String description;
}

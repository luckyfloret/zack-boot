package cn.hmg.zackblog.framework.common.enums;

import lombok.Getter;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-26 16:15
 * @description: 用户性别枚举
 */
@Getter
public enum UserSexEnum {

    /**
     * 用户性别枚举
     */
    MAN(1, "男"),
    WOMEN(2, "女"),
    ;


    UserSexEnum(Integer sex, String description) {
        this.sex = sex;
        this.description = description;
    }

    /**
     * 用户性别
     */
    private final Integer sex;

    /**
     * 性别描述
     */
    private final String description;
}

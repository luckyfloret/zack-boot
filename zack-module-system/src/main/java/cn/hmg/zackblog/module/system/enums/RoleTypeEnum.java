package cn.hmg.zackblog.module.system.enums;

import lombok.Getter;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-25 14:29
 * @description: 角色类型枚举
 */
@Getter
public enum RoleTypeEnum {

    /**
     * 角色类型枚举
     */
    SYSTEM(1, "内置"),
    CUSTOM(2, "自定义"),
    ;


    RoleTypeEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }


    private final Integer type;

    private final String description;
}

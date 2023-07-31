package cn.hmg.zackblog.framework.common.enums;

import lombok.Getter;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-12 16:15
 * @description: 用户类型枚举
 */
@Getter
public enum UserTypeEnum {

    /**
     * 前台用户 or 后台用户
     */
    FRONT_USER(1, "前台用户"),
    ADMIN_USER(2, "后台用户")
    ;

    UserTypeEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    /**
     * 用户类型
     */
    private final Integer type;

    /**
     * 类型描述
     */
    private final String description;
}

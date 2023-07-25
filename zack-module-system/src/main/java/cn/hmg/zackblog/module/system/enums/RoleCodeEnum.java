package cn.hmg.zackblog.module.system.enums;

import lombok.Getter;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-25 14:24
 * @description: 角色编码枚举
 */
@Getter
public enum RoleCodeEnum {

    /**
     * 角色编码枚举
     */
    SUPER_ADMIN("super_admin", "超级管理员"),
    NORMAL("normal", "普通用户");


    RoleCodeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }


    /**
     * 角色编码
     */
    private final String code;

    /**
     * 描述
     */
    private final String description;

    public static boolean isSuperAdmin(String code){
        return SUPER_ADMIN.getCode().equals(code);
    }

    public static boolean isNormal(String code){
        return NORMAL.getCode().equals(code);
    }
}

package cn.hmg.zackblog.module.system.enums;

import lombok.Getter;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-21 16:51
 * @description: 菜单类型枚举
 */
@Getter
public enum MenuTypeEnum {

    /**
     * 菜单类型枚举
     */
    DIR(1, "目录"),
    MENU(2, "菜单"),
    BUTTON(3, "按钮");


    MenuTypeEnum(Integer code, String description){
        this.code = code;
        this.description = description;
    }

    private final Integer code;

    private final String description;
}

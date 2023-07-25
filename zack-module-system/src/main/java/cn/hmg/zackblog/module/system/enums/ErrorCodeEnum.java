package cn.hmg.zackblog.module.system.enums;

import lombok.Getter;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-14 14:05
 * @description: 系统管理模块业务错误枚举，业务错误码范围：1-02-00-000，枚举信息名称以模块名为前缀，例如Auth模块：AUTH_XXX
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
    USER_USERNAME_EXISTS(10201000,"用户账号已存在"),
    USER_NOT_EXISTS(10201001, "用户不存在，请联系管理员！"),


    /**
     *  ############################ Role模块 10202000 ############################
     */
    ROLE_NOT_EXISTS(10202000, "角色不存在"),
    ROLE_NAME_ALREADY_EXISTS(10202001, "角色名称已存在，请更换角色名称"),
    ROLE_CODE_CANNOT_USE(10202002, "角色编码不能使用，请更换角色编码"),
    ROLE_STATUS_ERROR(10202003, "角色状态错误，只能是开启或关闭"),
    ROLE_CANNOT_OPERATE_SYSTEM_ROLE(10202004, "不能操作系统内置角色"),


    /**
     *  ############################ Menu模块 10203000 ############################
     */
    MENU_SET_PARENT_MENU_ERROR(10203000, "不能设置自己为父菜单"),
    MENU_PARENT_NOT_EXISTS(10203001, "父菜单不存在"),
    MENU_TYPE_ERROR(10203002, "菜单类型错误"),
    MENU_PRIMARY_KEY_ID_ERROR(10203003, "菜单id有误，请输入合法的id"),
    MENU_HAS_BEEN_ASSIGN(10203004, "菜单已经被分配，无法删除"),
    MENU_NOT_EXISTS(10203005, "菜单不存在"),
    MENU_EXISTS_CHILD(10203006, "子菜单存在，无法删除"),
    MENU_ALREADY_EXISTS(10203007, "菜单已存在"),
    ;


    ErrorCodeEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
    private final Integer code;
    private final String message;
}

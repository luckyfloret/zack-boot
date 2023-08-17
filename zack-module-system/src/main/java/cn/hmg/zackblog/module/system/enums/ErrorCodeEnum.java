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
    AUTH_REFRESH_TOKEN_ALREADY_EXPIRE(10200004, "刷新令牌已过期"),
    AUTH_INVALID_REFRESH_TOKEN(10200005, "无效的刷新令牌"),

    /**
     * ############################ User模块 10201000 ############################
     */
    USER_USERNAME_EXISTS(10201000,"用户账号已存在"),
    USER_NOT_EXISTS(10201001, "用户不存在，请联系管理员！"),
    USER_EMAIL_EXISTS(10201002, "邮箱已经被绑定，请换个邮箱试试"),
    USER_MOBILE_EXISTS(10201003, "手机号已经被绑定账号"),
    USER_STATUS_ERROR(10201004, "用户状态错误，只能是开启或关闭"),
    USER_TYPE_ERROR(10201005, "用户类型错误，只能是前台用户或后台用户"),
    USER_SEX_ERROR(10201006, "用户性别错误，只能是男或女"),
    USER_OLD_PASSWORD_ERROR(10201007, "旧密码错误"),


    /**
     *  ############################ Role模块 10202000 ############################
     */
    ROLE_NOT_EXISTS(10202000, "角色不存在"),
    ROLE_NAME_ALREADY_EXISTS(10202001, "角色名称已存在，请更换角色名称"),
    ROLE_CODE_CANNOT_USE(10202002, "角色编码不能使用，请更换角色编码"),
    ROLE_STATUS_ERROR(10202003, "角色状态错误，只能是开启或关闭"),
    ROLE_CANNOT_OPERATE_SYSTEM_ROLE(10202004, "不能操作系统内置角色"),
    ROLE_NOT_ALLOWED_ASSIGN_PERMISSION(10202005, "不允许分配权限"),
    ROLE_CODE_ALREADY_EXISTS(10202006, "角色编码已经存在，请更换角色编码"),


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


    /**
     * ############################ Dict Type模块 10204000 ############################
     */
    DICT_TYPE_NAME_ALREADY_EXISTS(10204000, "字典类型名称已存在"),
    DICT_TYPE_TYPE_ALREADY_EXISTS(10204001, "字典类型已存在"),
    DICT_TYPE_NOT_EXISTS(10204002, "字典类型不存在"),
    DICT_TYPE_EXISTS_DICT_DATA(10204003, "字典类型存在字典数据，无法删除"),
    DICT_TYPE_STATUS_ERROR(10204004, "字典类型状态错误，只能是开启或关闭"),
    DICT_TYPE_NOT_ENABLE(10204005, "该字典类型未启用，不能选择该字典类型"),


    /**
     * ############################ Dict Data模块 10205000 ############################
     */
    DICT_DATA_VALUE_ALREADY_EXISTS(10205000, "字典数据键值已存在"),
    DICT_DATA_STATUS_ERROR(10205001, "字典数据状态错误，只能是开启或关闭"),
    DICT_DATA_NOT_EXISTS(10205002, "字典数据不存在"),

    ;


    ErrorCodeEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
    private final Integer code;
    private final String message;
}

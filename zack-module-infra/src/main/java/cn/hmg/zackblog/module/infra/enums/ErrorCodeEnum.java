package cn.hmg.zackblog.module.infra.enums;

import lombok.Getter;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-14 14:05
 * @description: 基础设施模块业务错误枚举，业务错误码范围：1-01-00-000，枚举信息名称以模块名为前缀，例如file模块：FILE_XXX
 */
@Getter
public enum ErrorCodeEnum {

    /**
     * ############################ file config 模块 10100000 ############################
     */
    FILE_CONFIG_NOT_EXISTS(10100000, "文件配置不存在"),
    FILE_CONFIG_IS_MASTER(10100001, "该文件配置是主配置，不能删除"),

    /**
     * ############################ file 模块 10101000 ############################
     */
    FILE_NOT_EXISTS(10101000, "文件不存在"),
    FILE_CANNOT_EMPTY(10101001, "文件不能为空"),
    FILE_TYPE_IS_ONLY_PICTURE(10101002, "文件类型只能是 => [jpg，png，gif]"),
    ;



    ErrorCodeEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
    private final Integer code;
    private final String message;
}

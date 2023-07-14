package cn.hmg.zackblog.common.enums;

import lombok.Getter;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-14 23:22
 * @description: 公共状态枚举
 */
@Getter
public enum CommonStatusEnum {

    /**
     * 启用
     */
    ENABLED(0, "启用"),
    /**
     * 禁用
     */
    DISABLED(1, "禁用");

    CommonStatusEnum(Integer statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }


    /**
     * 状态码
     */
    private final Integer statusCode;

    /**
     * 描述
     */
    private final String description;
}

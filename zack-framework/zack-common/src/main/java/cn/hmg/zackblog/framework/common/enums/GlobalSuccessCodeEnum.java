package cn.hmg.zackblog.framework.common.enums;

import lombok.Getter;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-31 15:17
 * @description: 成功码枚举
 */
@Getter
public enum GlobalSuccessCodeEnum {

    /**
     * 成功码枚举
     */
    SUCCESS(200, "success"),;


    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 成功信息
     */
    private final String message;

    GlobalSuccessCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

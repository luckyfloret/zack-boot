package cn.hmg.zackblog.module.website.enums;

import lombok.Getter;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-02 23:59
 * @description: issues 状态枚举
 */
@Getter
public enum IssuesStatusEnum {

    /**
     * issues 状态枚举
     */
    TO_BE_SOLVED(1, "待解决"),
    ALREADY_SOLVED(2, "已解决"),
    ;



    IssuesStatusEnum(Integer status, String description) {
        this.status = status;
        this.description = description;
    }

    private final Integer status;

    private final String description;
}

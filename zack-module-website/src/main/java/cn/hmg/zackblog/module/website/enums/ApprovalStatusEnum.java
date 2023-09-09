package cn.hmg.zackblog.module.website.enums;

import lombok.Getter;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-30 18:06
 * @description: 审核状态
 */
@Getter
public enum ApprovalStatusEnum {

    /**
     * 审核状态
     */
    WAIT_REVIEW(1, "待审核"),
    PASS(2, "通过"),
    REJECT(3, "驳回"),
    ;

    ApprovalStatusEnum(Integer status, String description) {
        this.status = status;
        this.description = description;
    }

    private final Integer status;

    private final String description;
}

package cn.hmg.zackblog.framework.common.enums;

import lombok.Getter;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-30 23:37
 * @description: 邮箱发送状态枚举
 */
@Getter
public enum MailSendStatusEnum {
    /**
     * 邮箱发送状态枚举
     */
    SEND_SUCCESS(1, "发送成功"),
    SEND_FAIL(0, "发送失败"),
    ;

    MailSendStatusEnum(Integer status, String description) {
        this.status = status;
        this.description = description;
    }

    /**
     * 状态码
     */
    private final Integer status;

    /**
     * 描述
     */
    private final String description;

}

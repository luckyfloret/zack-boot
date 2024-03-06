package cn.hmg.zackblog.framework.common.enums;

import lombok.Getter;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-10-03 23:13
 * @description: 邮件模板编码枚举
 */
@Getter
public enum MailTemplateCodeEnum {
    /**
     * 邮件模板编码枚举
     */
    WEB_MAIL_LOGIN("web-login", "前台邮箱登录"),
    ;


    MailTemplateCodeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    private final String code;

    private final String description;
}

package cn.hmg.zackblog.module.website.enums;

import lombok.Getter;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-30 15:05
 * @description: 网站管理模块业务错误枚举，业务错误码范围：1-03-00-000，枚举信息名称以模块名为前缀，例如Comment模块：COMMENT_XXX
 */
@Getter
public enum ErrorCodeEnum {

    /**
     * ############################ FriendLink模块 10300000 ############################
     */
    FRIEND_LINK_NOT_EXISTS(10300000, "友情链接不存在"),
    FRIEND_LINK_BINDING_EMAIL_ALREADY_EXISTS(10300001, "该邮箱已绑定其他友情链接，请更换邮箱试试~"),
    FRIEND_LINK_BINDING_WEBSITE_URL_ALREADY_EXISTS(10300002, "该url已经绑定其他友情链接，请更换url试试~"),
    FRIEND_LINK_HAS_APPROVED(10300002, "该友情链接已审批过了，请不要重复审批！"),



    /**
     * ############################ Notice模块 10301000 ############################
     */
    NOTICE_NOT_EXISTS(10301000, "通知公告不存在"),
    NOTICE_ALREADY_ENABLE(10301001, "已经是启用状态，请勿重复操作"),
    NOTICE_STATUS_ERROR(10301002, "状态只能是开启或关闭"),
    NOTICE_TYPE_ERROR(10301003, "类型只能是通知或公告"),
    NOTICE_TYPE_INCONSISTENT_WITH_DB(10301004, "类型不一致"),


    /**
     * ############################ Issues模块 10302000 ############################
     */
    ISSUES_NOT_EXISTS(10302000, "issues不存在"),
    ISSUES_STATUS_ALREADY_UPDATE(10302001, "issues状态已更新过， 请勿重复更新！"),


    /**
     * ############################ Comment模块 10303000 ############################
     */
    COMMENT_NOT_EXISTS(10303000, "评论不存在"),


    /**
     * ############################ WebConfig模块 10304000 ############################
     */
    WEB_CONFIG_NOT_EXISTS(10304000, "网站配置不存在"),
    WEB_CONFIG_FILE_CANNOT_EMPTY(10304001, "图片不能为空"),
    ;


    ErrorCodeEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
    private final Integer code;
    private final String message;
}

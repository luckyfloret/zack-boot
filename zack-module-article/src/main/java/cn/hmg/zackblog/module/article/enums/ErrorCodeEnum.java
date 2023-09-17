package cn.hmg.zackblog.module.article.enums;

import lombok.Getter;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-16 15:59
 * @description: 文章管理模块业务错误枚举，业务错误码范围：1-04-00-000，枚举信息名称以模块名为前缀，例如Article模块：ARTICLE_XXX
 */
@Getter
public enum ErrorCodeEnum {
    /**
     * ############################ Article模块 10400000 ############################
     */
    ARTICLE_TYPE_NOT_EXISTS(10400000, "文章类型不存在"),
    ARTICLE_PUBLISH_STATUS_NOT_EXISTS(10400001, "文章发布状态不存在"),
    ARTICLE_RECOMMEND_STATUS_NOT_EXISTS(10400002, "文章推荐状态不存在"),
    ARTICLE_NOT_EXISTS(10400003, "文章不存在"),
    ARTICLE_BINDING_TAG_COUNT_ERROR(10400004, "文章最多只能绑定5个标签"),

    /**
     * ############################ Category模块 10401000 ############################
     */
    CATEGORY_NOT_EXISTS(10401000, "分类不存在"),
    CATEGORY_ALREADY_EXISTS(10401001, "分类已存在，请更换分类名称试试~"),
    CATEGORY_ALREADY_BOUND_ARTICLE(10401002, "该分类已经绑定文章，不能删除"),

    /**
     * ############################ Tags模块 10402000 ############################
     */
    TAGS_NOT_EXISTS(10402000, "标签不存在"),
    TAGS_ALREADY_EXISTS(10402001, "标签已存在，请更换标签名称试试~"),
    TAGS_ALREADY_BOUND_ARTICLE(10402002, "标签已绑定文章，不能删除"),

    ;

    ErrorCodeEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
    private final Integer code;
    private final String message;
}

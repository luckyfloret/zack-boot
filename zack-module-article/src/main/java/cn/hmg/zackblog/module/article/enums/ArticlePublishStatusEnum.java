package cn.hmg.zackblog.module.article.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-16 15:32
 * @description: 文章发布状态枚举
 */
@Getter
public enum ArticlePublishStatusEnum {
    /**
     * 文章发布状态枚举
     */
    PUBLISH(1, "发布"),
    NO_PUBLISH(2, "下架")
    ;

    ArticlePublishStatusEnum(Integer status, String description) {
        this.status = status;
        this.description = description;
    }

    /**
     * 状态
     */
    private final Integer status;

    /**
     * 描述
     */
    private final String description;

    /**
     * 校验状态是否存在
     * @param status 状态
     * @return true or false
     */
    public static boolean verifyPublishStatusIsExists(Integer status) {
        return Arrays.stream(values()).anyMatch(i -> i.getStatus().equals(status));
    }
}

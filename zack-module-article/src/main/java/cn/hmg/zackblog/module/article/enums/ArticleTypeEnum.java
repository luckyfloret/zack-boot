package cn.hmg.zackblog.module.article.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-16 15:31
 * @description: 文章类型枚举
 */
@Getter
public enum ArticleTypeEnum {

    /**
     * 文章类型枚举
     */
    ORIGINAL(1, "原创"),
    REPRINT(2, "转载"),
    ;


    ArticleTypeEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    /**
     * 类型
     */
    private final Integer type;

    /**
     * 描述
     */
    private final String description;

    /**
     * 校验类型是否存在
     * @param type 状态
     * @return true or false
     */
    public static boolean verifyTypeIsExists(Integer type) {
        return Arrays.stream(values()).anyMatch(i -> i.getType().equals(type));
    }
}

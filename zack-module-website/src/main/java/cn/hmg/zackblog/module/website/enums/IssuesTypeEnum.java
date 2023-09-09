package cn.hmg.zackblog.module.website.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-02 23:56
 * @description: Issues 类型枚举
 */
@Getter
public enum IssuesTypeEnum {

    /**
     *
     */
    DEMAND(1, "需求"),
    BUG(2, "Bug"),
    OTHER(3, "其他"),
    ;

    IssuesTypeEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    private final Integer type;

    private final String description;

    public static boolean verifyIssuesTypeIsExists(Integer type) {
        List<Integer> types = Arrays.stream(values()).map(IssuesTypeEnum::getType).collect(Collectors.toList());
        return types.contains(type);
    }
}

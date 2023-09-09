package cn.hmg.zackblog.module.website.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-01 16:57
 * @description: 通知公告类型
 */
@Getter
public enum NoticeTypeEnum {

    /**
     * 通知公告类型
     */
    NOTIFICATION(1, "通知"),
    NOTICE(2, "公告"),
    ;

    NoticeTypeEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    private final Integer type;

    private final String description;

    public static boolean verifyNoticeTypeIsExists(Integer type) {
        List<Integer> types = Arrays.stream(values()).map(NoticeTypeEnum::getType).collect(Collectors.toList());
        return types.contains(type);
    }
}

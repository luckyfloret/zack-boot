package cn.hmg.zackblog.framework.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-06 16:31
 * @description: 图片扩展名枚举
 */
@Getter
public enum CommonImageTypeEnum {
    /**
     * 图片扩展名枚举
     */
    JPG("image/jpeg"),
    PNG("image/png"),
    GIF("image/gif"),
    ;

    CommonImageTypeEnum(String type) {
        this.type = type;
    }

    private final String type;

    public static boolean verifyImageType(String type){
        return Arrays.stream(values()).map(CommonImageTypeEnum::getType).collect(Collectors.toList()).contains(type);
    }
}

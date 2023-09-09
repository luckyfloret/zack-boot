package cn.hmg.zackblog.framework.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-14 23:22
 * @description: 公共状态枚举
 */
@Getter
public enum CommonStatusEnum {

    /**
     * 启用
     */
    ENABLED(0, "启用"),
    /**
     * 禁用
     */
    DISABLED(1, "禁用");

    CommonStatusEnum(Integer statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }


    /**
     * 状态码
     */
    private final Integer statusCode;

    /**
     * 描述
     */
    private final String description;


    /**
     * 校验状态是否存在，存在返回true 否则返回false
     * @param status 状态
     * @return true or false
     */
    public static boolean verifyStatusIsExists(Integer status){
        List<Integer> statusList = Arrays.stream(CommonStatusEnum.values()).map(CommonStatusEnum::getStatusCode).collect(Collectors.toList());
        return statusList.contains(status);
    }
}

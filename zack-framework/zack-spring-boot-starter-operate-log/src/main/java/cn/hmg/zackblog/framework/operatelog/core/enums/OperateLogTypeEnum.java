package cn.hmg.zackblog.framework.operatelog.core.enums;


import lombok.Getter;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-29 22:47
 * @description: 操作日志类型枚举
 */
@Getter
public enum OperateLogTypeEnum {

    /**
     * other是因为可能有些接口无法归类，所以选用其他
     */
    QUERY(1, "查询"),
    CREATE(2, "新增"),
    UPDATE(3, "更新"),
    DELETE(4, "删除"),
    IMPORT(5, "导入"),
    EXPORT(6, "导出"),
    OTHER(7, "其他");

    OperateLogTypeEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    private final Integer type;

    private final String description;
}

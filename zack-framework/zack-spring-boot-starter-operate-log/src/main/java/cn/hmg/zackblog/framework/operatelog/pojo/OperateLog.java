package cn.hmg.zackblog.framework.operatelog.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-30 10:48
 * @description: 操作日志实体
 */
@Data
@Schema(name = "操作日志")
public class OperateLog {
    @Schema(description = "id (主键）")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "用户类型")
    private Integer userType;

    @Schema(description = "模块名")
    private String module;

    @Schema(description = "操作名")
    private String name;

    @Schema(description = "操作类型")
    private Integer type;

    @Schema(description = "请求方式")
    private String requestMethod;

    @Schema(description = "请求地址")
    private String requestUrl;

    @Schema(description = "用户ip")
    private String userIp;

    @Schema(description = "浏览器UA")
    private String userAgent;

    @Schema(description = "java方法名")
    private String javaMethod;

    @Schema(description = "java方法的参数")
    private String javaMethodArgs;

    @Schema(description = "操作时间")
    private LocalDateTime operateTime;

    @Schema(description = "执行时长")
    private Integer duration;

    @Schema(description = "结果码")
    private Integer resultCode;

    @Schema(description = "结果提示信息")
    private String resultMsg;

    @Schema(description = "结果数据")
    private String resultData;
}

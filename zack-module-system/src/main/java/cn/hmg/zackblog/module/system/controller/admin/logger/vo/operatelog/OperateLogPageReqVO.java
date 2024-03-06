package cn.hmg.zackblog.module.system.controller.admin.logger.vo.operatelog;

import cn.hmg.zackblog.framework.common.pojo.PageQueryParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-23 1:31
 * @description: 操作日志分页 request vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "操作日志分页 request vo")
public class OperateLogPageReqVO extends PageQueryParam {
    @Schema(description = "用户类型")
    private Integer userType;

    @Schema(description = "模块名")
    private String module;

    @Schema(description = "操作人")
    private String username;

    @Schema(description = "操作类型")
    private Integer type;

    @Schema(description = "操作结果")
    private Boolean result;

    @Schema(description = "操作时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}

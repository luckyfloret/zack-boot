package cn.hmg.zackblog.module.system.controller.admin.logger.vo.operatelog;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-23 1:31
 * @description: 操作日志分页 response vo
 */
@Data
@Schema(name = "操作日志分页 response vo")
public class OperateLogPageRespVO {
    @Schema(description = "id (主键）")
    private Long id;

    @Schema(description = "操作人")
    private String username;

    @Schema(description = "模块名")
    private String module;

    @Schema(description = "操作名")
    private String name;

    @Schema(description = "操作类型")
    private Integer type;

    @Schema(description = "操作结果")
    private Integer result;

    @Schema(description = "操作时间")
    private LocalDateTime operateTime;

    @Schema(description = "执行时长")
    private Integer duration;
}

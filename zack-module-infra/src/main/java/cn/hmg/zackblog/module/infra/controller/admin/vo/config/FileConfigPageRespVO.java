package cn.hmg.zackblog.module.infra.controller.admin.vo.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-28 13:59
 * @description:
 */
@Data
@Schema(name = "文件配置分页 response vo")
public class FileConfigPageRespVO{
    @Schema(description = "文件配置id")
    private Long id;

    @Schema(description = "配置名")
    private String name;

    @Schema(description = "是否为主配置")
    private Boolean master;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "备注")
    private String remark;
}

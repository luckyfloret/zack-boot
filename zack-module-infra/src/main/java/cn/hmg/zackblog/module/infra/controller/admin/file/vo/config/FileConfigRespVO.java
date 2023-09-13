package cn.hmg.zackblog.module.infra.controller.admin.file.vo.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-28 13:59
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "文件配置 response vo")
public class FileConfigRespVO  extends BaseFileConfigVO{
    @Schema(description = "文件配置id")
    private Long id;
}

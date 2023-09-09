package cn.hmg.zackblog.module.infra.controller.admin.vo.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-28 13:58
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "文件配置更新 request vo")
public class FileConfigUpdateReqVO extends BaseFileConfigVO {
    @Schema(description = "配置id")
    @NotNull(message = "id不能为空")
    private Long id;
}

package cn.hmg.zackblog.module.infra.controller.admin.file.vo.config;

import cn.hmg.zackblog.framework.common.pojo.PageQueryParam;
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
@Schema(name = "文件配置分页 request vo")
public class FileConfigPageReqVO extends PageQueryParam {
    @Schema(description = "配置名")
    private String name;
}

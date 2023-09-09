package cn.hmg.zackblog.module.infra.controller.admin.vo.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-06 16:56
 * @description: 文件分页列表 response vo
 */
@Schema(name = "文件分页列表 response vo")
@Data
public class FilePageRespVO {
    @Schema(description = "id (主键）")
    private Long id;

    @Schema(description = "配置id")
    private Long configId;

    @Schema(description = "文件名")
    private String name;

    @Schema(description = "oss文件名")
    private String ossFilename;

    @Schema(description = "文件类型")
    private String type;

    @Schema(description = "文件大小")
    private Integer size;
}

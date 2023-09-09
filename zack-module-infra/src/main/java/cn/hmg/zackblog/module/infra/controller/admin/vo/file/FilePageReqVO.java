package cn.hmg.zackblog.module.infra.controller.admin.vo.file;

import cn.hmg.zackblog.framework.common.pojo.PageQueryParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-06 16:57
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "文件分页列表 request vo")
public class FilePageReqVO extends PageQueryParam {
    @Schema(description = "文件名")
    private String filename;
}

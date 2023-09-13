package cn.hmg.zackblog.module.website.controller.admin.webconfig.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-07 0:32
 * @description: 网站配置上传 request vo
 */
@Data
@Schema(name = "网站配置上传 request vo")
public class WebConfigUploadReqVO {
    @Schema(description = "文件")
    private MultipartFile multipartFile;
}

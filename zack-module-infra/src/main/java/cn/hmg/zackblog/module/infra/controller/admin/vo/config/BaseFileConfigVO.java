package cn.hmg.zackblog.module.infra.controller.admin.vo.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-28 0:20
 * @description: 基础文件配置vo
 */
@Data
public class BaseFileConfigVO {
    @Schema(description = "配置名")
    @NotBlank(message = "配置名不能为空")
    private String name;

    /**
     * 节点地址
     */
    @NotBlank(message = "节点地址不能为空")
    private String endpoint;

    /**
     * 自定义域名
     */
    @NotBlank(message = "域名不能为空")
    @URL(message = "请输入正确的域名")
    private String domain;
    /**
     * 存储 Bucket
     */
    @NotBlank(message = "存储桶不能为空")
    private String bucket;

    /**
     * 访问 Key
     */
    @NotBlank(message = "accessKey不能为空")
    private String accessKey;
    /**
     * 访问 Secret
     */
    @NotBlank(message = "accessSecret不能为空")
    private String accessSecret;

    @Schema(description = "备注")
    private String remark;
}

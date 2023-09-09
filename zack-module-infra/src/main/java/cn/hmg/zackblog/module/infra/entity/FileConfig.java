package cn.hmg.zackblog.module.infra.entity;

import cn.hmg.zackblog.framework.mybatisplus.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 文件配置（例如本地、阿里OSS、七牛云上传等）
 * </p>
 *
 * @author hmg
 * @since 2023-08-27
 */
@Getter
@Setter
@TableName("infra_file_config")
@Schema(name = "FileConfig对象", description = "文件配置（例如本地、阿里OSS、七牛云上传等）")
public class FileConfig extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id （主键）")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "配置名")
    private String name;

    @Schema(description = "是否为主配置")
    private Boolean master;

    @Schema(description = "节点地址")
    private String endpoint;

    @Schema(description = "自定义域名")
    private String domain;

    @Schema(description = "存储 Bucket")
    private String bucket;

    @Schema(description = "访问 Key")
    private String accessKey;

    @Schema(description = "访问 Secret")
    private String accessSecret;

    @Schema(description = "区域")
    private String region;

    @Schema(description = "备注")
    private String remark;
}

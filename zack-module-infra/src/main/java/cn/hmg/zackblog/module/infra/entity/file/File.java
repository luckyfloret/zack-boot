package cn.hmg.zackblog.module.infra.entity.file;

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
 *
 * </p>
 *
 * @author hmg
 * @since 2023-08-27
 */
@Getter
@Setter
@TableName("infra_file")
@Schema(name = "file entity")
public class File extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id (主键）")
    @TableId(value = "id", type = IdType.AUTO)
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

    @Schema(description = "oss地址")
    private String url;
}

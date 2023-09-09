package cn.hmg.zackblog.module.system.entity.logger;

import cn.hmg.zackblog.framework.mybatisplus.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 操作日志
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("system_operate_log")
@Schema(name = "OperateLog对象", description = "操作日志")
public class OperateLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id (主键）")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "用户类型")
    private Integer userType;

    @Schema(description = "模块名")
    private String module;

    @Schema(description = "操作名")
    private String name;

    @Schema(description = "操作类型")
    private Integer type;

    @Schema(description = "请求方式")
    private String requestMethod;

    @Schema(description = "请求地址")
    private String requestUrl;

    @Schema(description = "用户ip")
    private String userIp;

    @Schema(description = "浏览器UA")
    private String userAgent;

    @Schema(description = "java方法名")
    private String javaMethod;

    @Schema(description = "java方法的参数")
    private String javaMethodArgs;

    @Schema(description = "操作时间")
    private LocalDateTime operateTime;

    @Schema(description = "执行时长")
    private Integer duration;

    @Schema(description = "结果码")
    private Integer resultCode;

    @Schema(description = "结果提示信息")
    private String resultMsg;

    @Schema(description = "结果数据")
    private String resultData;
}

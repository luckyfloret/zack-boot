package cn.hmg.zackblog.module.system.entity.logger;

import cn.hmg.zackblog.framework.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
@TableName("system_operate_log")
@ApiModel(value = "OperateLog对象", description = "操作日志")
public class OperateLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id (主键）")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户类型")
    private Byte userType;

    @ApiModelProperty("模块名")
    private String module;

    @ApiModelProperty("操作名")
    private String name;

    @ApiModelProperty("操作类型")
    private Byte type;

    @ApiModelProperty("请求方式")
    private String requestMethod;

    @ApiModelProperty("请求地址")
    private String requestUrl;

    @ApiModelProperty("用户ip")
    private String userIp;

    @ApiModelProperty("浏览器UA")
    private String userAgent;

    @ApiModelProperty("java方法名")
    private String javaMethod;

    @ApiModelProperty("java方法的参数")
    private String javaMethodArgs;

    @ApiModelProperty("操作时间")
    private LocalDateTime operateTime;

    @ApiModelProperty("执行时长")
    private Integer duration;

    @ApiModelProperty("结果码")
    private Integer resultCode;

    @ApiModelProperty("结果提示信息")
    private String resultMsg;

    @ApiModelProperty("结果数据")
    private String resultData;
}

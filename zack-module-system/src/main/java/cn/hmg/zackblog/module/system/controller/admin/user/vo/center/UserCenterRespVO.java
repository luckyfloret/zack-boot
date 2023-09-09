package cn.hmg.zackblog.module.system.controller.admin.user.vo.center;

import cn.hmg.zackblog.module.system.controller.admin.user.vo.BaseUserVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-20 23:42
 * @description: 用户个人中心 response vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "用户个人中心 response vo")
public class UserCenterRespVO extends BaseUserVO {
    @Schema(description = "用户id")
    private Long id;

    @Schema(description = "用户类型")
    private Integer type;

    @Schema(description = "最后登录 IP", required = true, example = "192.168.1.1")
    private String loginIp;

    @Schema(description = "最后登录时间", required = true, example = "时间戳格式")
    private LocalDateTime loginDate;

    @Schema(description = "创建时间", required = true, example = "时间戳格式")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 所属角色
     */
    private List<RoleVO> roles;


    @Data
    @Schema(name = "角色")
    public static class RoleVO{
        @Schema(description = "角色id")
        private Long id;

        @Schema(description = "角色名称")
        private String name;
    }
}

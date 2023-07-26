package cn.hmg.zackblog.module.system.controller.admin.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-26 14:44
 * @description: 用户分页 response vo
 */
@EqualsAndHashCode(callSuper = true)
@Schema(name = "用户分页 response vo")
@Data
public class UserPageRespVO extends BaseUserVO{
    @Schema(description = "id 主键")
    private Long id;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "最后登录ip")
    private String loginIp;

    @Schema(description = "最后登录时间")
    private LocalDateTime loginDate;
}

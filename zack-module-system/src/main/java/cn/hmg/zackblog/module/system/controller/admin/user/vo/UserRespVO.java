package cn.hmg.zackblog.module.system.controller.admin.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-26 17:23
 * @description: 用户 response vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "用户 response vo")
public class UserRespVO extends BaseUserVO{
    @Schema(description = "用户id")
    private Long id;
}

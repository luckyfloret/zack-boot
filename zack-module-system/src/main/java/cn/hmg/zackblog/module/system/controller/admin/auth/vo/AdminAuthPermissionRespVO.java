package cn.hmg.zackblog.module.system.controller.admin.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-18 10:26
 * @description: 权限编码 response vo
 */
@Schema(name = "权限编码 response vo")
@Data
@Builder
public class AdminAuthPermissionRespVO {
    private Set<String> permissions;
}

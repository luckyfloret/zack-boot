package cn.hmg.zackblog.module.website.controller.admin.vo.issues;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-03 0:03
 * @description: issues 分页 response vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "issues 分页 response vo")
public class IssuesPageRespVO extends BaseIssuesVO{
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}

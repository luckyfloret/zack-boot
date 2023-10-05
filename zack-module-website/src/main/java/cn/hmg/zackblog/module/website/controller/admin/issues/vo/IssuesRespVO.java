package cn.hmg.zackblog.module.website.controller.admin.issues.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-03 0:03
 * @description: issues response vo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "issues response vo")
public class IssuesRespVO extends BaseIssuesVO{
    @Schema(description = "内容")
    private String content;
}

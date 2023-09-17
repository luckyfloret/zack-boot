package cn.hmg.zackblog.module.article.controller.admin.tags.vo;

import cn.hmg.zackblog.framework.common.pojo.PageQueryParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-16 23:54
 * @description: 标签分页 request vo
 */
@Data
@Schema(name = "标签分页 request vo")
public class TagsPageReqVO extends PageQueryParam {
    @Schema(description = "标签名")
    private String tagName;
}

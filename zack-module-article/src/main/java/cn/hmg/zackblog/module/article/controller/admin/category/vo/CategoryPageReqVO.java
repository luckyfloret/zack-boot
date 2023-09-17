package cn.hmg.zackblog.module.article.controller.admin.category.vo;

import cn.hmg.zackblog.framework.common.pojo.PageQueryParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-16 21:10
 * @description: 分类分页 request vo
 */
@Data
@Schema(name = "分类分页 request vo")
public class CategoryPageReqVO extends PageQueryParam {
    @Schema(description = "分类名称")
    private String categoryName;
}

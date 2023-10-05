package cn.hmg.zackblog.module.article.controller.admin.category.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-16 21:10
 * @description: 分类分页 response vo
 */
@Schema(name = "分类分页 response vo")
@Data
public class CategoryPageRespVO extends BaseCategoryVO {
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}

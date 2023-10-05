package cn.hmg.zackblog.module.article.controller.admin.category.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-10-04 16:22
 * @description: 简单的分类列表 response vo
 */
@Data
@Schema(name = "简单的分类列表 response vo")
public class CategoryListSimpleRespVO {
    private Long id;

    private String categoryName;
}

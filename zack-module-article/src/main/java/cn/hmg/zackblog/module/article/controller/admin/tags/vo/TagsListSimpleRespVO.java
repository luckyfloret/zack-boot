package cn.hmg.zackblog.module.article.controller.admin.tags.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-10-04 16:05
 * @description: 简单的标签列表 response vo
 */
@Data
@Schema(name = "简单的标签列表 response vo")
public class TagsListSimpleRespVO {
    private Long id;

    private String tagName;
}

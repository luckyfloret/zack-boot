package cn.hmg.zackblog.module.article.controller.admin.tags.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-16 23:53
 * @description: 标签分页 response vo
 */
@Data
@Schema(name = "标签分页 response vo")
public class TagsPageRespVO extends BaseTagsVO{
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}

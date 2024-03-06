package cn.hmg.zackblog.module.article.entity.category;

import cn.hmg.zackblog.framework.mybatisplus.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * <p>
 * 分类管理
 * </p>
 *
 * @author hmg
 * @since 2023-09-11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("biz_category")
@Schema(name = "Category对象", description = "分类管理")
public class Category extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id  主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "点击量")
    private String clickVolume;

    @Schema(description = "排序")
    private Integer sort;
}

package cn.hmg.zackblog.module.website.entity;

import cn.hmg.zackblog.framework.mybatisplus.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 问题管理表
 * </p>
 *
 * @author hmg
 * @since 2023-08-30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("biz_issues")
@Schema(name = "Issues对象", description = "问题管理表")
public class Issues extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id 主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "问题类型（1 需求、2 bug、3 其他）")
    private Integer type;

    @Schema(description = "状态（1 待解决 2 已解决）")
    private Integer status;
}

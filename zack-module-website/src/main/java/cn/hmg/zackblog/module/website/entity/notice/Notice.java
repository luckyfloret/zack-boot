package cn.hmg.zackblog.module.website.entity.notice;

import cn.hmg.zackblog.framework.mybatisplus.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 消息管理
 * </p>
 *
 * @author hmg
 * @since 2023-08-30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("biz_notice")
@Schema(name = "Notice对象", description = "消息管理")
public class Notice extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id 主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "类型 （1 通知、2 公告）每一种类型只能启用一条")
    private Integer type;

    @Schema(description = "状态（0 正常、1 关闭）")
    private Integer status;
}

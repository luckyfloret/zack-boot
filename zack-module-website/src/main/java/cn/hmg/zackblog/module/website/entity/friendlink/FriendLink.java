package cn.hmg.zackblog.module.website.entity.friendlink;

import cn.hmg.zackblog.framework.mybatisplus.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 友情链接
 * </p>
 *
 * @author hmg
 * @since 2023-08-30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("biz_friend_link")
@Schema(name = "FriendLink对象", description = "友情链接")
public class FriendLink extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id 主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "网站名称")
    private String name;

    @Schema(description = "网站简介")
    private String description;

    @Schema(description = "网站头像url")
    private String avatarUrl;

    @Schema(description = "邮箱地址")
    private String email;

    @Schema(description = "网站地址")
    private String websiteUrl;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "审批状态（1 待审核、2 通过、3 驳回）")
    private Integer approvalStatus;

    @Schema(description = "驳回意见")
    private String rejectOpinion;
}

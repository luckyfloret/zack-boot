package cn.hmg.zackblog.module.system.entity.permission;

import cn.hmg.zackblog.framework.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 用户和角色的关联表
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Getter
@Setter
@TableName("system_user_role")
@Schema(name = "UserRole对象", description = "用户和角色的关联表")
public class UserRole extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "角色id")
    private Long roleId;
}

package cn.hmg.zackblog.module.system.entity.permission;

import cn.hmg.zackblog.framework.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "UserRole对象", description = "用户和角色的关联表")
public class UserRole extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("角色id")
    private Long roleId;
}

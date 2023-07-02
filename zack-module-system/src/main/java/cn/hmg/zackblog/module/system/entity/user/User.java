package cn.hmg.zackblog.module.system.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户管理
 * </p>
 *
 * @author hmg
 * @since 2023-07-02
 */
@Getter
@Setter
@TableName("system_users")
@ApiModel(value = "Users对象", description = "用户管理")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id 主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("用户类型（1 前台用户， 2 后台用户）")
    private Byte type;

    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("手机号码")
    private String mobile;

    @ApiModelProperty("用户性别")
    private Byte sex;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("用户状态（0 正常、1 停用）")
    private Byte status;

    @ApiModelProperty("最后登录ip")
    private String loginIp;

    @ApiModelProperty("最后登录时间")
    private LocalDateTime loginDate;
}

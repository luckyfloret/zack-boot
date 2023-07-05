package cn.hmg.zackblog.framework.core.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-03 23:06
 * @description: 公共实体类
 */
@Data
public abstract class BaseEntity implements Serializable {
    private static final Long serialVersionUID = 1L;

    private Long creator;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime createTime;

    private Long updater;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Boolean deleted;
}

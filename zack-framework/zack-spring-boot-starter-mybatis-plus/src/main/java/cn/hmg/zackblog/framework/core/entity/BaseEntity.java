package cn.hmg.zackblog.framework.core.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-03 23:06
 * @description: 公共实体类
 */
@Data
public abstract class BaseEntity implements Serializable {
    private static final Long serialVersionUID = 1L;

}

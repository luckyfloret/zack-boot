package cn.hmg.zackblog.module.system.controller.admin.permission.vo;

import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-17 23:08
 * @description: 菜单列表请求参数
 */
@Data
public class MenuListReqVO {
    private String name;

    private Integer status;
}

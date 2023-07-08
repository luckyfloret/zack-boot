package cn.hmg.zackblog.common.pojo;

import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-08 15:36
 * @description: 通用分页参数
 */
@Data
public class PageQueryParam {
    /**
     * 页码
     */
    private Integer pageNum;

    /**
     *  页大小
     */
    private Integer pageSize;
}

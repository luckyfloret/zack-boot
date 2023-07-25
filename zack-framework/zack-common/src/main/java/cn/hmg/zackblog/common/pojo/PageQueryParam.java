package cn.hmg.zackblog.common.pojo;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-08 15:36
 * @description: 通用分页参数
 */
@Data
public class PageQueryParam {

    private final Integer PAGE_NUM = 1;

    private final Integer PAGE_SIZE = 10;


    /**
     * 页码
     */
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码不能小于1")
    private Integer pageNum;

    /**
     * 页大小
     */
    @NotNull(message = "页大小不能为空")
    @Min(value = 1, message = "每页大不能小于1")
    @Max(value = 400, message = "每页大小不能大于400")
    private Integer pageSize;
}

package cn.hmg.zackblog.common.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-08 15:25
 * @description:  封装分页结果
 */
@Data
public class PageResult<T> implements Serializable {

    /**
     * 数据
     */
    private List<T> data;

    /**
     * 总记录数
     */
    private Long total;

    public PageResult(){}

    public PageResult(List<T> data, Long total) {
        this.data = data;
        this.total = total;
    }

    public PageResult(Long total) {
        this.data = new ArrayList<>();
        this.total = total;
    }

    /**
     * 获取空的PageResult
     * @return PageResult
     */
    public static <T> PageResult<T> empty(){
        return new PageResult<>(0L);
    }
}

package cn.hmg.zackblog.framework.core.mapper;

import cn.hmg.zackblog.common.pojo.PageQueryParam;
import cn.hmg.zackblog.common.pojo.PageResult;
import cn.hmg.zackblog.framework.core.query.LambdaQueryWrapperExtend;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-08 15:19
 * @description: BaseMapper的扩展接口
 */
public interface BaseMapperExtend<T> extends BaseMapper<T> {

    default PageResult<T> page(PageQueryParam pageQueryParam, Wrapper<T> queryWrapper) {
        IPage<T> page = selectPage(new Page<>(pageQueryParam.getPageNum(), pageQueryParam.getPageSize()), queryWrapper);

        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    /**
     * 查询一个
     *
     * @param field 字段名
     * @param value 值
     * @return T
     */
    default T selectOne(SFunction<T, ?> field, Object value) {
        return selectOne(new LambdaQueryWrapperExtend<T>().eq(field, value));
    }

    default T selectOne(SFunction<T, ?> field1, Object value1, SFunction<T, ?> field2, Object value2) {
        return selectOne(new LambdaQueryWrapperExtend<T>().eq(field1, value1).eq(field2, value2));
    }

    default T selectOne(SFunction<T, ?> field1, Object value1, SFunction<T, ?> field2, Object value2, SFunction<T, ?> field3, Object value3) {
        return selectOne(new LambdaQueryWrapperExtend<T>().eq(field1, value1).eq(field2, value2).eq(field3, value3));
    }

    default Long selectCount(SFunction<T, ?> field1, Object value1) {
        return selectCount(new LambdaQueryWrapperExtend<T>().eq(field1, value1));
    }

    default List<T> selectList() {
        return selectList(new QueryWrapper<>());
    }

    default void delete(SFunction<T, ?> field1, Object value1) {
        delete(new LambdaQueryWrapperExtend<T>().eq(field1, value1));
    }
}

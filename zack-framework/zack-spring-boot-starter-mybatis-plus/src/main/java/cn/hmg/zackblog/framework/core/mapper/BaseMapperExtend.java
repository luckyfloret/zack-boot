package cn.hmg.zackblog.framework.core.mapper;

import cn.hmg.zackblog.framework.core.query.LambdaQueryWrapperExtend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-08 15:19
 * @description: BaseMapper的扩展接口
 */
public interface BaseMapperExtend<T> extends BaseMapper<T> {
    /**
     * 查询一个
     * @param field 字段名
     * @param value 值
     * @return T
     */
    default T selectOne(SFunction<T, ?> field, Object value){
        return selectOne(new LambdaQueryWrapperExtend<T>().eq(field, value));
    }
}

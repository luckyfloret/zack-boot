package cn.hmg.zackblog.framework.mybatisplus.core.query;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

import java.util.Objects;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-08 15:20
 * @description: LambdaQueryWrapper的扩展类
 */
public class LambdaQueryWrapperExtend<T> extends LambdaQueryWrapper<T> {
    public LambdaQueryWrapperExtend<T> likeIfExists(SFunction<T, ?> column, String value){
        if (StrUtil.isNotEmpty(value)) {
            return (LambdaQueryWrapperExtend<T>) super.like(column, value);
        }
        return this;
    }
        public LambdaQueryWrapperExtend<T> eqIfExists(SFunction<T, ?> column, Object value){
        if (Objects.nonNull(value)) {
            return (LambdaQueryWrapperExtend<T>) super.eq(column, value);
        }
        return this;
    }
}

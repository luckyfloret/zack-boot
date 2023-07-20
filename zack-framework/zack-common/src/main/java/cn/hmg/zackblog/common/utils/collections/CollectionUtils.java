package cn.hmg.zackblog.common.utils.collections;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-20 23:08
 * @description: 集合工具类
 */
public class CollectionUtils {

    public static <T, K, A, U, R> Map<K, R> convertMap(List<T> data, Function<? super T, ? extends K> classifier,
                                                                    Function<? super T, ? extends U> mapper,
                                                                    Collector<? super U, A, R> downstream){
        return data.stream().collect(Collectors.groupingBy(classifier,
                Collectors.mapping(mapper, downstream)));
    }
}

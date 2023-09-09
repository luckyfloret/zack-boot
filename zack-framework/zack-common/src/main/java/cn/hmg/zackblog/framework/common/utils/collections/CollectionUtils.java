package cn.hmg.zackblog.framework.common.utils.collections;

import java.util.*;
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


    @SafeVarargs
    public static <T> Set<T> asSet(T... value) {
        return new HashSet<>(Arrays.asList(value));
    }

    /**
     * 任何一个为空都直接返回false
     *
     * @param collection 集合
     * @return boolean
     */
    public static boolean isAnyEmpty(Collection<?>... collection) {
        return Arrays.stream(collection).anyMatch(CollectionUtils::isEmpty);
    }

    public static <T> boolean isEmpty(Collection<T> collection) {
        return (collection == null || collection.isEmpty());
    }

    public static <K, T> Map<K, T> convertMap(Collection<T> val, Function<T, K> key) {
        if (isEmpty(val)) {
            return new HashMap<>();
        }
        return val.stream().collect(Collectors.toMap(key, Function.identity()));
    }

    public static <T, K, A, U, V> Map<K, V> convertMapByGrouping(List<T> val, Function<T, K> classifier,
                                                                 Function<T, U> mapper,
                                                                 Collector<U, A, V> downstream) {
        if (isEmpty(val)) {
            return new HashMap<>();
        }
        return val.stream().collect(Collectors.groupingBy(classifier,
                Collectors.mapping(mapper, downstream)));
    }


    public static <T, F> Set<F> convetSet(List<T> value, Function<T, F> field) {
        if (isEmpty(value)) {
            return Collections.emptySet();
        }
        return value.stream().map(field).collect(Collectors.toSet());
    }

    public static <E> List<E> sort(List<E> value, Comparator<? super E> c) {
        value.sort(c);
        return value;
    }

}

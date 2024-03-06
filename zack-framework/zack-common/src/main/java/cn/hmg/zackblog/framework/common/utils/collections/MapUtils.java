package cn.hmg.zackblog.framework.common.utils.collections;

import java.util.*;

import static cn.hmg.zackblog.framework.common.utils.collections.CollectionUtils.isEmpty;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-22 23:16
 * @description: Map工具类
 */
public class MapUtils {
    public static <K, V> Set<V> mapConvertSet(Map<K, Set<V>> data, Collection<K> key) {
        Set<V> result = new HashSet<>();
        key.forEach(k -> {
            Set<V> values = data.get(k);
            if (isEmpty(values)) {
                return;
            }
            result.addAll(values);
        });
        return result;
    }
}

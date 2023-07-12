package cn.hmg.zackblog.common.utils.date;

import java.time.LocalDateTime;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-12 15:27
 * @description: 日期工具类
 */
public class DateUtils {

    /**
     * 判断传进来的日期是否过期
     * @param expireTime 过期时间
     * @return 过期 return true else return false
     */
    public static boolean isExpire(LocalDateTime expireTime){
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.isAfter(expireTime);
    }
}

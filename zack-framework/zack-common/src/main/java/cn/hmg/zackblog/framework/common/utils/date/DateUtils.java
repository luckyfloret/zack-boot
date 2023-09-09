package cn.hmg.zackblog.framework.common.utils.date;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-12 15:27
 * @description: 日期工具类
 */
public class DateUtils {

    private final static String TIME_ZONE = "Asia/Shanghai";



    /**
     * 判断传进来的日期是否过期
     * @param expireTime 过期时间
     * @return 过期 return true else return false
     */
    public static boolean isExpire(LocalDateTime expireTime){
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.isAfter(expireTime);
    }


    public static long toSecond(LocalDateTime localDateTime){
        return localDateTime.toEpochSecond(getOffset(localDateTime));
    }

    public static int subtract(int val1, int val2) {
        return val1 - val2;
    }

    public static ZoneOffset getOffset(LocalDateTime localDateTime){
        return getZoneId().getRules().getOffset(localDateTime);
    }

    public static ZoneId getZoneId(){
        return ZoneId.of(TIME_ZONE);
    }
}

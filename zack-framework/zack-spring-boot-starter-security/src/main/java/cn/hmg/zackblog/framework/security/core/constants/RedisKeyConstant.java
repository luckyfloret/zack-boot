package cn.hmg.zackblog.framework.security.core.constants;

import cn.hmg.zackblog.framework.redis.core.definition.RedisKeyDefinition;
import cn.hmg.zackblog.framework.security.core.pojo.LoginUser;

import java.util.concurrent.TimeUnit;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-12 11:50
 * @description:  Redis Key 常量
 */
public interface RedisKeyConstant {

    /**
     * 访问令牌与刷新令牌的redis key过期时间
     */
    Integer EXPIRE_TIME = 999999999;

    /**
     *
     * 访问令牌和刷新令牌的redis key过期时间保持一致，以防访问令牌的redis key比刷新令牌的redis key先过期，
     * 这样会导致某些问题的发生，例如用户登出，需要删除accessToken与refreshToken的缓存，那么refreshToken需要从accessToken缓存信息拿到
     * 如果accessToken redis key 比 refreshToken先过期，就会导致拿不到refreshToken，删除不了refreshToken的缓存，就会导致用户登出了，
     * 仍是可以拿着refreshToken去刷新令牌，拿到最新的accessToken，等于假退出登录
     *
     */
    RedisKeyDefinition ACCESS_TOKEN =
            new RedisKeyDefinition("access_token:%s", LoginUser.class, EXPIRE_TIME, TimeUnit.SECONDS, "缓存访问令牌与用户信息");

    RedisKeyDefinition REFRESH_TOKEN = new RedisKeyDefinition("refresh_token:%s", String.class, EXPIRE_TIME, TimeUnit.SECONDS, "缓存刷新令牌");
}

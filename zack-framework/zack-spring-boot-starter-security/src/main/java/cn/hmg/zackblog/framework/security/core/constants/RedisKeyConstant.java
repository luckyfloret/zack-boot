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
    RedisKeyDefinition ACCESS_TOKEN =
            new RedisKeyDefinition("access_token:%s", LoginUser.class, 99999999, TimeUnit.SECONDS, "缓存访问令牌与用户信息");

    RedisKeyDefinition REFRESH_TOKEN = new RedisKeyDefinition("refresh_token:%s", String.class, 999999999, TimeUnit.SECONDS, "缓存刷新令牌");
}

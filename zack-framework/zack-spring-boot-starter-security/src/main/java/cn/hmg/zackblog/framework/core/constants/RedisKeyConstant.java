package cn.hmg.zackblog.framework.core.constants;

import cn.hmg.zackblog.framework.core.definition.RedisKeyDefinition;
import cn.hmg.zackblog.framework.core.pojo.LoginUser;

import java.util.concurrent.TimeUnit;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-12 11:50
 * @description:  Redis Key 常量
 */
public interface RedisKeyConstant {
    RedisKeyDefinition ACCESS_TOKEN =
            new RedisKeyDefinition("access_token:%s", LoginUser.class, 4200, TimeUnit.SECONDS, "缓存令牌与用户信息");
}

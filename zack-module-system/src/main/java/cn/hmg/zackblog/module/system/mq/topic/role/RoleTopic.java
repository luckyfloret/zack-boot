package cn.hmg.zackblog.module.system.mq.topic.role;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-28 17:36
 * @description: 角色topic
 */
public class RoleTopic {
    public static final String ROLE_REFRESH_CACHE = "system_role_refresh_cache";
    public static final String CONSUMER_GROUP = "role_refresh_consumer_group";

    /**
     * tag以业务模块区分
     */
    public static final String TAG = "system_role";
}

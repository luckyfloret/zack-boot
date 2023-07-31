package cn.hmg.zackblog.module.system.mq.topic.permission;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-28 17:36
 * @description: 权限topic
 */
public class PermissionTopic {
    public static final String PERMISSION_REFRESH_CACHE = "system_permission_refresh_cache";
    public static final String CONSUMER_GROUP = "permission_refresh_consumer_group";

    /**
     * tag以业务模块区分
     */
    public static final String TAG = "system_permission";
}

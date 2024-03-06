package cn.hmg.zackblog.module.infra.mq.topic.file.config;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-28 23:23
 * @description: 文件配置 topic
 */
public class FileConfigTopic {
    public static final String FILE_CONFIG_REFRESH_CACHE = "infra_file_config_refresh_cache";
    public static final String CONSUMER_GROUP = "infra_file_config_refresh_consumer_group";

    /**
     * tag以业务模块区分
     */
    public static final String TAG = "infra_file_config";
}

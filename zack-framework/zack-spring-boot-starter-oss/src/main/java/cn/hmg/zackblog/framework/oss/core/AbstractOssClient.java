package cn.hmg.zackblog.framework.oss.core;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-25 13:57
 * @description: 抽象模板，用于初始化配置
 */
@Slf4j
public abstract class AbstractOssClient<Config> implements OssClient {

    /**
     * oss配置，用于ossClient
     */
    protected Config config;

    private final Long configId;

    public AbstractOssClient(Long configId, Config config) {
        this.configId = configId;
        this.config = config;
    }

    public void init(){
        initStorageConfig();
        log.info("{} -> 文件存储器配置初始化...", this.getClass().getSimpleName());
    }

    protected abstract void initStorageConfig();

    @Override
    public Long getConfigId() {
        return configId;
    }
}

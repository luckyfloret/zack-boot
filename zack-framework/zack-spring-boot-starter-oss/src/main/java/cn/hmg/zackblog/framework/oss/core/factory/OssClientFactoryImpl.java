package cn.hmg.zackblog.framework.oss.core.factory;

import cn.hmg.zackblog.framework.oss.core.AbstractOssClient;
import cn.hmg.zackblog.framework.oss.core.client.s3.OssClientImpl;
import cn.hmg.zackblog.framework.oss.core.client.s3.OssConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-27 16:03
 * @description: Oss工厂实现类
 */
@Slf4j
public class OssClientFactoryImpl implements OssClientFactory {
    private final ConcurrentMap<Long, AbstractOssClient<OssConfig>> storageClient = new ConcurrentHashMap<>();

    @Override
    public AbstractOssClient<OssConfig> getOssClient(Long configId) {
        AbstractOssClient<OssConfig> fileStorage = storageClient.get(configId);
        if (Objects.isNull(fileStorage)) {
            log.error("文件存储器客户端不存在，id：{}", configId);
        }
        return fileStorage;
    }

    @Override
    public AbstractOssClient<OssConfig> createOssClient(Long configId, OssConfig storageConfig) {
        AbstractOssClient<OssConfig> fileStorage = new OssClientImpl(configId, storageConfig);
        fileStorage.init();
        storageClient.put(configId, fileStorage);
        return fileStorage;
    }

    @Override
    public void clearCache() {
        storageClient.clear();
    }
}

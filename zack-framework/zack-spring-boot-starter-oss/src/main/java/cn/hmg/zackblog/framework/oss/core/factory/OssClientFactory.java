package cn.hmg.zackblog.framework.oss.core.factory;

import cn.hmg.zackblog.framework.oss.core.AbstractOssClient;
import cn.hmg.zackblog.framework.oss.core.client.s3.OssConfig;


/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-27 16:02
 * @description: Oss工厂
 */
public interface OssClientFactory {

    AbstractOssClient<OssConfig> getOssClient(Long configId);

    AbstractOssClient<OssConfig> createOssClient(Long configId, OssConfig storageConfig);

    void clearCache();
}

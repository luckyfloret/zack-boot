package cn.hmg.zackblog.framework.oss.core.client.s3;

import cn.hmg.zackblog.framework.oss.core.AbstractOssClient;
import cn.hutool.core.io.IoUtil;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-25 13:41
 * @description: oss客户端
 */
public class OssClientImpl extends AbstractOssClient<OssConfig> {

    private MinioClient minioClient;

    public OssClientImpl(Long configId, OssConfig config) {
        super(configId, config);
    }

    @Override
    protected void initStorageConfig() {
        minioClient = MinioClient.builder()
                .endpoint(config.getEndpoint())
                .credentials(config.getAccessKey(), config.getAccessSecret())
                .build();
    }

    @Override
    public String upload(byte[] fileContent, String filename, String contentType) throws Exception {
        minioClient.putObject(
                PutObjectArgs.builder()
                        .stream(IoUtil.toStream(fileContent), fileContent.length, -1)
                        .contentType(contentType)
                        .bucket(config.getBucket())
                        .object(filename)
                        .build());
        return config.getDomain() + "/" + filename;
    }

    @Override
    public void delete(String filename) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(config.getBucket())
                .object(filename)
                .build());
    }
}

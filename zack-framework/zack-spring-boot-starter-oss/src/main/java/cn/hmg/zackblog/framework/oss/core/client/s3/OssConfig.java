package cn.hmg.zackblog.framework.oss.core.client.s3;

import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-25 15:26
 * @description: oss配置
 */
@Data
public class OssConfig {
    public static final String ENDPOINT_QINIU = "qiniucs.com";
    public static final String ENDPOINT_ALIYUN = "aliyuncs.com";
    public static final String ENDPOINT_TENCENT = "myqcloud.com";

    /**
     * 节点地址
     */
    private String endpoint;
    /**
     * 自定义域名
     */
    private String domain;
    /**
     * 存储 Bucket
     */
    private String bucket;

    /**
     * 区域
     */
    private String region;

    /**
     * 访问 Key
     */
    private String accessKey;
    /**
     * 访问 Secret
     */
    private String accessSecret;
}

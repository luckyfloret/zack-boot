package cn.hmg.zackblog.framework.oss.core;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-25 13:37
 * @description: Oss Service
 */
public interface OssClient {

    Long getConfigId();

    /**
     * oss上传
     * @param fileContent 文件内容
     * @param filename 文件名
     * @param contentType 文件类型
     * @return oss url
     * @throws Exception
     */
    String upload(byte[] fileContent, String filename, String contentType) throws Exception;

    /**
     * oss删除
     * @param filename 文件名
     * @throws Exception
     */
    void delete(String filename) throws Exception;
}

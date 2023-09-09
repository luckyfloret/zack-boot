package cn.hmg.zackblog.framework.oss.autoconfigure;

import cn.hmg.zackblog.framework.oss.core.factory.OssClientFactory;
import cn.hmg.zackblog.framework.oss.core.factory.OssClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-24 16:42
 * @description: 文件存储客户端自动配置类
 */
@AutoConfiguration
public class ZackOssAutoConfiguration {
    @Bean
    public OssClientFactory ossClientFactory(){
        return new OssClientFactoryImpl();
    }
}

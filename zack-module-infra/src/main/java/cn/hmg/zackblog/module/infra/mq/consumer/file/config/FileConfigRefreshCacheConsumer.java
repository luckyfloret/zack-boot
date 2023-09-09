package cn.hmg.zackblog.module.infra.mq.consumer.file.config;

import cn.hmg.zackblog.framework.rocketmq.core.listener.AbstractRocketMQListener;
import cn.hmg.zackblog.module.infra.mq.producer.file.config.FileConfigRefreshCacheMessage;
import cn.hmg.zackblog.module.infra.mq.topic.file.config.FileConfigTopic;
import cn.hmg.zackblog.module.infra.service.IFileConfigService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static cn.hmg.zackblog.module.infra.mq.topic.file.config.FileConfigTopic.CONSUMER_GROUP;
import static cn.hmg.zackblog.module.infra.mq.topic.file.config.FileConfigTopic.FILE_CONFIG_REFRESH_CACHE;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-28 23:22
 * @description:
 */
@Component
@RocketMQMessageListener(topic = FILE_CONFIG_REFRESH_CACHE, consumerGroup = CONSUMER_GROUP)
public class FileConfigRefreshCacheConsumer extends AbstractRocketMQListener<FileConfigRefreshCacheMessage> implements RocketMQListener<FileConfigRefreshCacheMessage> {

    @Resource
    private IFileConfigService fileConfigService;

    @Override
    protected String consumerName() {
        return "FileConfigRefreshCacheConsumer";
    }

    @Override
    protected void handleMessage(FileConfigRefreshCacheMessage message) throws Exception {
        fileConfigService.initLocalCache();
    }

    @Override
    protected void overMaxRetryTimesMessage(FileConfigRefreshCacheMessage message) {

    }

    @Override
    protected boolean isRetry() {
        return false;
    }

    @Override
    protected boolean isThrowException() {
        return false;
    }

    @Override
    public void onMessage(FileConfigRefreshCacheMessage message) {
        super.dispatch(message);
    }
}

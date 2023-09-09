package cn.hmg.zackblog.module.infra.mq.producer.file.config;

import cn.hmg.zackblog.framework.rocketmq.core.message.AbstractMessageTemplate;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-08-28 23:25
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FileConfigRefreshCacheMessage extends AbstractMessageTemplate {
    private String message;
}

package cn.hmg.zackblog.module.article.mq.producer.tags;

import cn.hmg.zackblog.framework.rocketmq.core.message.AbstractMessageTemplate;
import lombok.Data;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-17 11:26
 * @description: 标签刷新缓存消息模板
 */
@Data
public class TagsRefreshCacheMessage extends AbstractMessageTemplate {
    /**
     * 消息
     */
    private String message;
}

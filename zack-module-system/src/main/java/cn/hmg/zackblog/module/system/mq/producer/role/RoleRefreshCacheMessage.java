package cn.hmg.zackblog.module.system.mq.producer.role;

import cn.hmg.zackblog.framework.rocketmq.core.message.AbstractMessageTemplate;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-28 17:16
 * @description: 角色刷新缓存消息
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleRefreshCacheMessage extends AbstractMessageTemplate {
    private String message;
}

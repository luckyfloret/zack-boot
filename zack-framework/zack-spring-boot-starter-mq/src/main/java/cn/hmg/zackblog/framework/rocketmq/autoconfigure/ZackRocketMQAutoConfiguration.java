package cn.hmg.zackblog.framework.rocketmq.autoconfigure;

import cn.hmg.zackblog.framework.rocketmq.core.RocketMQTemplateExt;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-28 0:07
 * @description: rocketmq自动配置类
 */
@AutoConfiguration
@ConditionalOnProperty(matchIfMissing = true, prefix = "zack.rocketmq", value = "enabled", havingValue = "true")
public class ZackRocketMQAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public RocketMQTemplateExt rocketMQTemplateExt(RocketMQTemplate rocketMQTemplate){
        return new RocketMQTemplateExt(rocketMQTemplate);
    }
}

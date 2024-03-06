package cn.hmg.zackblog.framework.banner.autoconfigure;

import cn.hmg.zackblog.framework.banner.core.CustomizeBannerApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-29 16:32
 * @description: banner自动配置类
 */
@AutoConfiguration
public class ZackBannerAutoConfiguration {
    @Bean
    public CustomizeBannerApplicationRunner customizeBannerApplicationRunner(){
        return new CustomizeBannerApplicationRunner();
    }
}

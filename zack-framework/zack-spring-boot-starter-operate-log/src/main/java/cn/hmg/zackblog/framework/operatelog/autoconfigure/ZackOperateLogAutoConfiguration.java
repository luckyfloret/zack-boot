package cn.hmg.zackblog.framework.operatelog.autoconfigure;

import cn.hmg.zackblog.framework.operatelog.core.api.OperateLogApi;
import cn.hmg.zackblog.framework.operatelog.core.aspect.OperateLogAspect;
import cn.hmg.zackblog.framework.operatelog.core.service.OperateLogFrameworkService;
import cn.hmg.zackblog.framework.operatelog.core.service.OperateLogFrameworkServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-29 22:08
 * @description:
 */
@AutoConfiguration
public class ZackOperateLogAutoConfiguration {
    @Bean
    public OperateLogAspect operateLogAspect(){
        return new OperateLogAspect();
    }

    @Bean
    public OperateLogFrameworkService operateLogFrameworkService(OperateLogApi operateLogApi){
        return new OperateLogFrameworkServiceImpl(operateLogApi);
    }
}

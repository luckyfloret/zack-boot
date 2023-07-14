package cn.hmg.zackblog.framework.config;

import cn.hmg.zackblog.framework.core.handler.DefaultMetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-03 16:35
 * @description: MybatisPlus自动配置类
 */
@AutoConfiguration
@MapperScan(basePackages = "${zack.info.base-package:cn.hmg.zackblog}", annotationClass = Mapper.class)
public class ZackMybatisPlusAutoConfiguration {
    /**
     * 分页插件
     * @return MybatisPlusInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

    /**
     * 默认字段填充器
     * @return DefaultMetaObjectHandler
     */
    @Bean
    public DefaultMetaObjectHandler defaultMetaObjectHandler(){
        return new DefaultMetaObjectHandler();
    }
}

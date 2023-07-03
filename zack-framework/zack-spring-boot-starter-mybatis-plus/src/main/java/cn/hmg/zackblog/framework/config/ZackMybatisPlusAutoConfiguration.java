package cn.hmg.zackblog.framework.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import javax.annotation.PostConstruct;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-03 16:35
 * @description: MybatisPlus自动配置类
 */
@AutoConfiguration
@MapperScan(basePackages = "${zack.info.base-package}", annotationClass = Mapper.class)
public class ZackMybatisPlusAutoConfiguration {
}

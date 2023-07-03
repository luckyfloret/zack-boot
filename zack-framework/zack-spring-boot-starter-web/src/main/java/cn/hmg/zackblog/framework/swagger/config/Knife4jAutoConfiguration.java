package cn.hmg.zackblog.framework.swagger.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-02 23:46
 * @description:
 */
@AutoConfiguration
@EnableSwagger2WebMvc
public class Knife4jAutoConfiguration {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        //.title("swagger-bootstrap-ui-demo RESTful APIs")
                        .description("ZackBlog RESTful APIs")
                        .termsOfServiceUrl("https://www.zack6.cn")
                        .contact(new Contact("hmg", "https://www.zack6.cn", "2663069776@qq.com"))
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("2.X版本")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("cn.hmg.zackblog.module.system.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}

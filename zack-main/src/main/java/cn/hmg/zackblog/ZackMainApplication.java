package cn.hmg.zackblog;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-06-30 16:46
 * @description: 主启动
 */
//@SuppressWarnings("SpringComponentScan")
@SpringBootApplication
public class ZackMainApplication {

    @Value("${zack.info.base-package}")
    private String basePackage;

    @PostConstruct
    public void init(){
        System.out.println("base package ==> " + basePackage);
    }
    public static void main(String[] args) {
        SpringApplication.run(ZackMainApplication.class, args);
    }
}

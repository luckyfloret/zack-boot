package cn.hmg.zackblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-06-30 16:46
 * @description: 主启动
 */
@SpringBootApplication(scanBasePackages = "cn.hmg.zackblog")
@EnableCaching
public class ZackMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZackMainApplication.class, args);
    }
}

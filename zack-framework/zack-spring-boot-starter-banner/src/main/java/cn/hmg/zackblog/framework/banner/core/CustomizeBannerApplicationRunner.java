package cn.hmg.zackblog.framework.banner.core;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.net.Inet4Address;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-29 16:34
 * @description: 应用启动完成后定制banner
 */
@Slf4j
public class CustomizeBannerApplicationRunner implements ApplicationRunner {

    @Value("${server.port}")
    private String serverPort;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ThreadUtil.execAsync(() -> {
            //睡眠1秒保证信息输出到最底部
            ThreadUtil.sleep(1000);
            try {
                log.info("\n\t=====================================项目启动完成=====================================" +
                                "\n \t\t\t\t local knife4j接口文档地址：{}" +
                                "\n \t\t\t\t ip knife4j接口文档地址：{}" +
                                "\n\t====================================================================================",
                        "http://" + Inet4Address.getLoopbackAddress().getHostAddress() + ":" + serverPort + "/doc.html",
                        "http://" + Inet4Address.getLocalHost().getHostAddress() + ":" + serverPort + "/doc.html"
                );
            } catch (Exception e) {
                log.error("exception message => ", e);
            }
        });
    }
}

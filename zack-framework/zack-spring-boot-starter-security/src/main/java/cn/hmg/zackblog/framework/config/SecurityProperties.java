package cn.hmg.zackblog.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-05 15:50
 * @description: spring security 属性配置类
 */
@ConfigurationProperties(prefix = "zack.security")
@Data
@Validated
public class SecurityProperties {
    /**
     *  token header
     */
    @NotBlank(message = "token header不能为空")
    private String header = "Authorization";

    /**
     * token 过期时间（默认为 30分钟）
     */
    @NotNull(message = "过期时间不能为空")
    private Integer expire = 1800;

    /**
     *  免登录的url
     */
    private List<String> permitAllUrls = new ArrayList<>();
}

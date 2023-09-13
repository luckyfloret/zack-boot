package cn.hmg.zackblog.framework.security.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
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
     *  免登录的url
     */
    private List<String> permitAllUrls = new ArrayList<>();

    /**
     * 访问令牌过期时间，单位为秒
     */
    private long accessTokenExpireTime = 1800;

    /**
     * 刷新令牌过期时间，单位为秒
     */
    private long refreshTokenExpireTime = 4200;
}

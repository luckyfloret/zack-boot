package cn.hmg.zackblog.framework.captcha.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

import static cn.hmg.zackblog.framework.captcha.autoconfigure.CaptchaProperties.PREFIX;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-13 22:20
 * @description: 验证码配置
 */
@Data
@Validated
@ConfigurationProperties(prefix = PREFIX)
public class CaptchaProperties {
    public static final String PREFIX = "zack.captcha";


    /**
     * 默认开启，值为true
     */
    @NotNull(message = "验证码开关不能为空")
    private Boolean enabled = true;
}

package com.tio.app.common.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 图片验证码生成类
 *
 * @author zj chen <britton@126.com>
 * @date 2018/11/26 17:37
 */
@Component
@Configuration
@Data
@ConfigurationProperties(prefix = "kaptcha")
@PropertySource(value = {"classpath:/ymls/kaptcha.yml"}, encoding = "utf-8")
public class KaptchaConfig {
    @Value("${border}")
    private String border;

    @Value("${textproducer.font.color}")
    private String fontColor;

    @Value("${textproducer.char.space}")
    private String charSpace;

    @Bean
    public DefaultKaptcha captcha() {
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.textproducer.font.color", fontColor);
        properties.put("kaptcha.textproducer.char.space", charSpace);
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}

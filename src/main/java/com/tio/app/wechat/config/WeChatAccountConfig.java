package com.tio.app.wechat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "wx.mp.configs")
public class WeChatAccountConfig {
    /**
     * 消息的加密KEY
     */
    private String mpAesKey;

    /**
     * TOKEN
     */
    private String mpToken;

    /**
     * 公众平台id
     */
    private String mpAppId;

    /**
     * 公众平台密钥
     */
    private String mpAppSecret;

    /**
     * 开放平台id
     */
    private String openAppId;

    /**
     * 开放平台密钥
     */
    private String openAppSecret;

    /**
     * 微信模板id
     */
    private Map<String, String> templateId;
}

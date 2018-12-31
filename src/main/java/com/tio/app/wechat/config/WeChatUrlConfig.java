package com.tio.app.wechat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "wx.project.url")
@Component
public class WeChatUrlConfig {
    /**
     * 微信公众平台授权url
     */
    public String wxMpAuthorize;

    /**
     * 微信开放平台授权url
     */
    public String wxOpenAuthorize;

    /**
     * 点餐系统
     */
    public String sell;
}

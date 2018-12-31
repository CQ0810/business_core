package com.tio.app.pay.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "wechat", ignoreUnknownFields = false)
@PropertySource("classpath:/ymls/wechat.yml")
public class WeChatConstant {
	
	@Value("${appId}")
	private String appId;
	
	@Value("${appSecret}")
	private String appSecret;
	
	@Value("${mchId}")
	private String mchId;
	
	@Value("${mchKey}")
	private String mchKey;
	
	@Value("${keyPath}")
	private String keyPath;
	
	@Value("${notifyUrl}")
	private String notifyUrl;

	@Value("${certLocalPath}")
	private String certLocalPath;

	@Value("${certRootPath}")
	private String certRootPath;
}

package com.tio.app.generate.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "upload")
@PropertySource(value = {"classpath:/ymls/upload.yml"}, encoding = "utf-8")
public class UploadConstant {
    @Value("${depositPath}")
    private String depositPath;

    @Value("${returnPath}")
    private String returnPath;
}

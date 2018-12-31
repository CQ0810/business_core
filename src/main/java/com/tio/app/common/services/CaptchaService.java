package com.tio.app.common.services;

import java.util.Map;

/**
 * 生成验证码接口
 */
public interface CaptchaService {

    Map<String, Object> getCaptcha();

    boolean checkCaptcha(String uuid, String code);
}

package com.tio.app.common.utils;

/**
 * Redis所有Keys
 */
public class RedisKeysUtil {
    /**
     * 获取系统配置的KEY
     *
     * @param key
     * @return
     */
    public static String getSysConfigKey(String key) {
        return "sys:config:" + key;
    }

    /**
     * 获取图形验证码的KEY
     *
     * @param key
     * @return
     */
    public static String getCaptchaConfigKey(String key) {
        return "sys:captcha:" + key;
    }

    /**
     * 获取手机验证码的KEY
     *
     * @param key
     * @return
     */
    public static String getSMSCodeConfigKey(String key) {
        return "sys:sms:" + key;
    }
}
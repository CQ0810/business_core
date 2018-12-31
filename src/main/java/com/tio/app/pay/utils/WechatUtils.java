package com.tio.app.pay.utils;

import com.alibaba.fastjson.JSONException;
import com.tio.app.common.utils.AuthCodeUtil;
import com.tio.app.common.utils.JsonMapUtil;
import com.tio.app.common.utils.TimeUtil;
import com.tio.app.pay.pojo.WeChatPay;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class WechatUtils {
    /**
     * TOKEN加密的KEY
     */
    private final static String KEY = "TIO_WECHAT_TOKEN_KEY";

    /**
     * TOKEN过期时间
     */
    private final static int EXPIRES = 30 * 60;

    /**
     * TOKEN要过期警告时间
     */
    private final static int EXPIRES_WARNING = 2 * 60;

    /**
     * 生成加密字符串(含过期时间)
     *
     * @param source 加密对象
     * @param expiry 过期时间
     * @return
     */
    public static String generateToken(Map<String, Object> source, int expiry) {
        source.put("expires", TimeUtil.getCurrentUnixTimestamp() + expiry);
        return AuthCodeUtil.authCodeEncode(JsonMapUtil.map2Json(source), KEY, expiry);
    }

    /**
     * 生成加密字符串
     *
     * @param source 加密对象
     * @return
     */
    public static String generateToken(Map<String, Object> source) {
        source.put("expires", TimeUtil.getCurrentUnixTimestamp() + EXPIRES);
        return AuthCodeUtil.authCodeEncode(JsonMapUtil.map2Json(source), KEY);
    }

    /**
     * 解码字符串
     *
     * @param source
     * @return
     */
    public static WeChatPay decodeToken(String source) {
        try {
            String json = AuthCodeUtil.authCodeDecode(source, KEY);
            return JsonMapUtil.parseToJavaObject(json, WeChatPay.class);
        } catch (JSONException exception) {
            log.error(exception.getMessage());
            return null;
        }
    }
}

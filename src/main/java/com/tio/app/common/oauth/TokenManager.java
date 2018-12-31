package com.tio.app.common.oauth;

import com.alibaba.fastjson.JSONException;
import com.tio.app.common.utils.AuthCodeUtil;
import com.tio.app.common.utils.JsonMapUtil;
import com.tio.app.common.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TokenManager {
    /**
     * TOKEN加密的KEY
     */
    private final static String KEY = "TIO_SHIRO_TOKEN_KEY";

    /**
     * TOKEN_KEY
     */
    public final static String USERTOKEN = "user_id";
    
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
    public static Map<String, Object> decodeToken(String source) {
        try {
            String s = AuthCodeUtil.authCodeDecode(source, KEY);
            Map<String, Object> map = JsonMapUtil.json2Map(s);
            int currentTime = TimeUtil.getCurrentUnixTimestamp();
            if ((int) map.get("expires") < currentTime) {
                return null;
            } else if ((int) map.get("expires") - currentTime < EXPIRES_WARNING) {
                map.put("refresh", true);
            }
            return map;
        } catch (JSONException exception) {
            log.error(exception.getMessage());
            return null;
        }
    }


    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", 1);
        map.put("id", 1);
        System.out.println(TokenManager.generateToken(map));
    }
}

package com.tio.app.common.utils;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * 在和PHP通信时的数据签名
 * 此类不可随便更改，否则可能导致双方的数据签名不能匹配
 *
 * @author zj chen <britton@126.com>
 */
public class SignatureUtil {
    /**
     * 生成签名.
     *
     * @param data 待签名数据
     * @param key  API密钥
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data, String key) throws Exception {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals("sign")) {
                continue;
            }
            String s = data.get(k) + ' ';
            if (s.trim().length() > 0) { // 参数值为空，则不参与签名
                sb.append(k).append("=").append(s.trim()).append("&");
            }
        }
        sb.append("ZerDoor_sign_key=").append(key);
        System.out.println(sb.toString());
        return MD5(sb.toString()).toUpperCase();
    }

    /**
     * 生成 MD5
     *
     * @param data 待处理数据
     * @return MD5结果
     */
    private static String MD5(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }
}

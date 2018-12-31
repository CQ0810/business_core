package com.tio.app.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CommonUtil {
    /**
     * MD5函数
     *
     * @param str 原始字符串
     * @return MD5结果
     */
    public static String MD5(String str) {
        StringBuffer sb = new StringBuffer();
        String part;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5 = md.digest(str.getBytes());
            for (int i = 0; i < md5.length; i++) {
                part = Integer.toHexString(md5[i] & 0xFF);
                if (part.length() == 1) {
                    part = "0" + part;
                }
                sb.append(part);
            }
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }
}

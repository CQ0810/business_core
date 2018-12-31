package com.tio.app.common.utils;

import org.springframework.util.Base64Utils;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Random;

/**
 * 用于和PHP通信时的加解密，加解密双方的数据
 * 此类不可随便更改，否则可能导致双方加解密不成功
 *
 * @author zj chen <britton@126.com>
 */
public class AuthCodeUtil {
    /**
     * 操作类型
     */
    public enum DisCuzAuthCodeMode {
        Encode, Decode
    }

    /**
     * private static MD5 md5 = new MD5();
     * private static BASE64 base64 = new BASE64();
     * 从字符串的指定位置截取指定长度的子字符串
     *
     * @param str        原字符串
     * @param startIndex 子字符串的起始位置
     * @param length     子字符串的长度
     * @return 子字符串
     */
    private static String CutString(String str, int startIndex, int length) {
        if (startIndex >= 0) {
            if (length < 0) {
                length = length * -1;
                if (startIndex - length < 0) {
                    length = startIndex;
                    startIndex = 0;
                } else {
                    startIndex = startIndex - length;
                }
            }
            if (startIndex > str.length()) {
                return "";
            }
        } else {
            if (length < 0) {
                return "";
            } else {
                if (length + startIndex > 0) {
                    length = length + startIndex;
                    startIndex = 0;
                } else {
                    return "";
                }
            }
        }
        if (str.length() - startIndex < length) {
            length = str.length() - startIndex;
        }
        return str.substring(startIndex, startIndex + length);
    }

    /**
     * 从字符串的指定位置开始截取到字符串结尾的了符串
     *
     * @param str        原字符串
     * @param startIndex 子字符串的起始位置
     * @return 子字符串
     */
    private static String CutString(String str, int startIndex) {
        return CutString(str, startIndex, str.length());
    }

    /**
     * 返回文件是否存在
     *
     * @param filename 文件名
     * @return boolean
     */
    public static boolean FileExists(String filename) {
        File f = new File(filename);
        return f.exists();
    }

    /**
     * MD5函数
     *
     * @param str 原始字符串
     * @return MD5结果
     */
    private static String MD5(String str) {
        // return md5.convert(str);
        return CommonUtil.MD5(str);
    }

    /**
     * 字段串是否为Null或为""(空)
     *
     * @param str
     * @return
     */
    public static boolean StrIsNullOrEmpty(String str) {
        if (str == null || str.trim().equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 用于 RC4 处理密码
     *
     * @param pass
     * @param kLen 密钥长度，一般为256
     * @return
     */
    static private byte[] GetKey(byte[] pass, int kLen) {
        byte[] mBox = new byte[kLen];
        for (int i = 0; i < kLen; i++) {
            mBox[i] = (byte) i;
        }
        int j = 0;
        for (int i = 0; i < kLen; i++) {
            j = (j + (int) ((mBox[i] + 256) % 256) + pass[i % pass.length]) % kLen;
            byte temp = mBox[i];
            mBox[i] = mBox[j];
            mBox[j] = temp;
        }
        return mBox;
    }

    /**
     * 生成随机字符
     *
     * @param lens 随机字符长度
     * @return 随机字符
     */
    private static String RandomString(int lens) {
        char[] CharArray = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        int cLens = CharArray.length;
        String sCode = "";
        Random random = new Random();
        for (int i = 0; i < lens; i++) {
            sCode += CharArray[Math.abs(random.nextInt(cLens))];
        }
        return sCode;
    }

    /**
     * 使用 DisCuz authCode 方法对字符串加密
     *
     * @param source 原始字符串
     * @param key    密钥
     * @param expiry 加密字串有效时间，单位是秒
     * @return 加密结果
     */
    public static String authCodeEncode(String source, String key, int expiry) {
        return authCode(source, key, DisCuzAuthCodeMode.Encode, expiry);
    }

    /**
     * 使用 DisCuz authCode 方法对字符串加密
     *
     * @param source 原始字符串
     * @param key    密钥
     * @return 加密结果
     */
    public static String authCodeEncode(String source, String key) {
        return authCode(source, key, DisCuzAuthCodeMode.Encode, 0);
    }

    /**
     * 使用 DisCuz authCode 方法对字符串解密
     *
     * @param source 原始字符串
     * @param key    密钥
     * @return 解密结果
     */
    public static String authCodeDecode(String source, String key) {
        return authCode(source, key, DisCuzAuthCodeMode.Decode, 0);
    }

    /**
     * 使用 变形的 rc4 编码方法对字符串进行加密或者解密
     *
     * @param source    原始字符串
     * @param key       密钥
     * @param operation 操作 加密还是解密
     * @param expiry    加密字串过期时间
     * @return 加密或者解密后的字符串
     */
    private static String authCode(String source, String key, DisCuzAuthCodeMode operation, int expiry) {
        try {
            if (source == null || key == null) {
                return "";
            }
            int cKeyLength = 4;
            String keyA, keyB, keyC, cryptKey, result;
            key = MD5(key);
            keyA = MD5(CutString(key, 0, 16));
            keyB = MD5(CutString(key, 16, 16));
            keyC = cKeyLength > 0 ? (operation == DisCuzAuthCodeMode.Decode ? CutString(
                    source, 0, cKeyLength) : RandomString(cKeyLength))
                    : "";
            cryptKey = keyA + MD5(keyA + keyC);
            if (operation == DisCuzAuthCodeMode.Decode) {
                byte[] temp;
                temp = Base64Utils.decode(CutString(source, cKeyLength).getBytes());
                result = new String(RC4(temp, cryptKey));
                if (CutString(result, 10, 16).equals(
                        CutString(MD5(CutString(result, 26) + keyB), 0, 16))) {
                    return CutString(result, 26);
                } else {
                    temp = Base64Utils.decode(CutString(source + "=", cKeyLength).getBytes());
                    result = new String(RC4(temp, cryptKey));
                    if (CutString(result, 10, 16)
                            .equals(CutString(
                                    MD5(CutString(result, 26) + keyB), 0, 16))) {
                        return CutString(result, 26);
                    } else {
                        temp = Base64Utils.decode(CutString(source + "==", cKeyLength).getBytes());
                        result = new String(RC4(temp, cryptKey));
                        if (CutString(result, 10, 16).equals(
                                CutString(MD5(CutString(result, 26) + keyB), 0,
                                        16))) {
                            return CutString(result, 26);
                        } else {
                            return "2";
                        }
                    }
                }
            } else {
                source = "0000000000" + CutString(MD5(source + keyB), 0, 16) + source;
                byte[] temp = RC4(source.getBytes("GBK"), cryptKey);
                return keyC + Base64Utils.encodeToString(temp);
            }
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * RC4 原始算法
     *
     * @param input 原始字串数组
     * @param pass  密钥
     * @return 处理后的字串数组
     */
    private static byte[] RC4(byte[] input, String pass) {
        if (input == null || pass == null) {
            return null;
        }
        byte[] output = new byte[input.length];
        byte[] mBox = GetKey(pass.getBytes(), 256);
        // 加密
        int i = 0;
        int j = 0;
        for (int offset = 0; offset < input.length; offset++) {
            i = (i + 1) % mBox.length;
            j = (j + (int) ((mBox[i] + 256) % 256)) % mBox.length;
            byte temp = mBox[i];
            mBox[i] = mBox[j];
            mBox[j] = temp;
            byte a = input[offset];
            // byte b = mBox[(mBox[i] + mBox[j] % mBox.Length) % mBox.Length];
            // mBox[j] 一定比 mBox.Length 小，不需要在取模
            byte b = mBox[(toInt(mBox[i]) + toInt(mBox[j])) % mBox.length];
            output[offset] = (byte) ((int) a ^ (int) toInt(b));
        }
        return output;
    }

    /**
     * 将字节转换为数字
     *
     * @param b
     * @return
     */
    private static int toInt(byte b) {
        return (int) ((b + 256) % 256);
    }

    /**
     * 获取当前Unix时间戳
     *
     * @return
     */
    public long getUnixTimestamp() {
        Calendar cal = Calendar.getInstance();
        return cal.getTimeInMillis() / 1000;
    }
}

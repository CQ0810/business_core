package com.tio.app.common.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 生成随机通讯码工具类
 * Created by admin on 2016/5/4.
 */
public class RandomStrUtils {
    public final static Integer STR_TYPE = 1;

    public final static Integer INT_TYPE = 2;

    private static final long RPC_SEQ_NO_NOT_REPEAT_INTERVAL = 5 * 1000;

    private final static Object lock = new Object();

    private static RandomStrUtils instance;

    private Map<String, Long> randomStrMap = new ConcurrentHashMap<>();

    private static final String[] BASE_STRING = new String[]{
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z", "A", "B", "C", "D",
            "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    private static final String[] BASE_INTEGER = new String[]{
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
    };

    private RandomStrUtils() {
    }

    public static RandomStrUtils getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new RandomStrUtils();
            }
        }
        return instance;
    }

    public String getRandomString(int type, int len) {
        Long nowTime = System.currentTimeMillis();
        String randomStr;
        synchronized (lock) {
            // 生成随机字符串
            randomStr = createRandomString(type, len, nowTime);
            // 删除一分钟前的随机字符串
            Iterator<Map.Entry<String, Long>> it = randomStrMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Long> entry = it.next();
                Long value = entry.getValue();
                if (nowTime - value > RPC_SEQ_NO_NOT_REPEAT_INTERVAL) {
                    it.remove();
                }
            }
        }
        return randomStr;
    }

    private String createRandomString(int type, int len, Long nowTime) {
        Random random = new Random();
        String[] strArray = type == STR_TYPE ? BASE_STRING : BASE_INTEGER;
        int length = strArray.length;
        StringBuilder randomSB = new StringBuilder();
        for (int i = 0; i < length; i++) {
            randomSB.append(strArray[random.nextInt(length)]);
        }
        String randomString = randomSB.toString();
        random = new Random(System.currentTimeMillis());
        StringBuilder strSB = new StringBuilder();
        for (int i = 0; i < len; i++) {
            strSB.append(randomString.charAt(random.nextInt(randomString.length() - 1)));
        }
        String resultStr = strSB.toString();
        // 判断一分钟内是否重复
        Long randomStrCreateTime = randomStrMap.get(resultStr);
        if (randomStrCreateTime != null && nowTime - randomStrCreateTime < RPC_SEQ_NO_NOT_REPEAT_INTERVAL) {
            resultStr = createRandomString(type, len, nowTime);
        }
        randomStrMap.put(resultStr, nowTime);
        return resultStr;
    }
}

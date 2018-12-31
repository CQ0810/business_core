package com.tio.app.common.services.impl;

import com.alibaba.druid.util.StringUtils;
import com.tio.app.common.pojo.HttpClientResult;
import com.tio.app.common.services.SendSMSService;
import com.tio.app.common.utils.*;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 网易云短信服务
 */
@Service
@Component
@Data
@ConfigurationProperties(prefix = "netease", ignoreUnknownFields = false)
@PropertySource(value = {"classpath:/ymls/netease-sms.yml"}, encoding = "utf-8")
public class NetEaseSMSServiceImpl implements SendSMSService {
    private static Logger logger = LoggerFactory.getLogger(NetEaseSMSServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;

    //网易云分配的账号
    @Value("${secretId}")
    private String secretId;

    //私钥
    @Value("${secretKey}")
    private String secretKey;

    //验证码
    @Value("${bussinessId}")
    private String bussinessId;

    //通知类短信
    @Value("${informUrl}")
    private String informUrl;

    //运营类短信
    @Value("${opertingClass}")
    private String opertingClass;

    //验证码长度
    @Value("${codeLen}")
    private int codeLen;

    //当前版本号
    @Value("${version}")
    private String version;

    //短信接口地址
    @Value("${apiUrl}")
    private String apiUrl;

    //模板ID
    @Value("${templateId}")
    private String templateId;

    //短信参数(签名)
    @Value("${params}")
    private String params;

    @Override
    public Map<String, Object> send(String phone) {
        Map<String, Object> data = new HashMap<>();
        Map<String, String> result = new HashMap<>();
        Map<String, String> postParams = new HashMap<>();
        // 1.设置公共参数
        postParams.put("secretId", secretId);
        postParams.put("businessId", bussinessId);
        postParams.put("version", version);
        postParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
        postParams.put("nonce", UUIDUtil.randomUUID());
        postParams.put("mobile", phone);
        postParams.put("templateId", templateId);
        String verificationCode = RandomStrUtils.getInstance().getRandomString(RandomStrUtils.INT_TYPE, codeLen);
        postParams.put("params", params + verificationCode);
        //需要上行的时候,这里需要设置为true
        postParams.put("needUp", "true");
        // 3.生成签名信息
        String signature;
        try {
            signature = genSignature(secretKey, postParams);
        } catch (Exception e) {
            logger.error("网易云短信接口签名错误", e);
            result.put("msg", "签名错误");
            data.put("data", result);
            return data;
        }
        postParams.put("signature", signature);
        try {
            HttpClientResult resultData = HttpClientUtil.doPost(apiUrl, postParams);
            storeCode(phone, verificationCode);
            result.put("msg", resultData.getContent());
            data.put("data", result);
        } catch (Exception e) {
            logger.error("网易云短信接口发送错误", e);
            result.put("msg", e.getMessage());
            data.put("data", result);
        }
        return data;
    }

    /**
     * 储存手机验证码到redis
     *
     * @param phone
     * @param code
     */
    private void storeCode(String phone, String code) {
        redisUtil.set(RedisKeysUtil.getSMSCodeConfigKey(phone), code, 5 * 60);
    }

    /**
     * 验证手机验证码
     *
     * @param phone
     * @param code
     * @return
     */
    public int verification(String phone, String code) {
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)){
            return 0;
        }
        String key = RedisKeysUtil.getSMSCodeConfigKey(phone);
        String s = redisUtil.get(key);
        if (s.equals(code)) {
            return 1;
        }
        return 0;
    }

    /**
     * 生成签名信息
     *
     * @param secretKey 产品私钥
     * @param params    接口请求参数名和参数值map，不包括signature参数名
     * @return
     */
    private String genSignature(String secretKey, Map<String, String> params) throws UnsupportedEncodingException {
        // 1. 参数名按照ASCII码表升序排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        // 2. 按照排序拼接参数名与参数值
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            sb.append(key).append(params.get(key));
        }
        // 3. 将secretKey拼接到最后
        sb.append(secretKey);
        // 4. MD5是128位长度的摘要算法，转换为十六进制之后长度为32字符
        return DigestUtils.md5Hex(sb.toString().getBytes("UTF-8"));
    }
}

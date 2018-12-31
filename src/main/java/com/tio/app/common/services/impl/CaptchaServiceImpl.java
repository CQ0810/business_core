package com.tio.app.common.services.impl;

import com.google.code.kaptcha.Producer;
import com.tio.app.common.services.CaptchaService;
import com.tio.app.common.utils.RedisKeysUtil;
import com.tio.app.common.utils.RedisUtil;
import com.tio.app.common.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class CaptchaServiceImpl implements CaptchaService {
    @Autowired
    private Producer producer;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Map<String, Object> getCaptcha() {
        return createCaptcha();
    }

    @Override
    public boolean checkCaptcha(String uuid, String code) {
        String key = RedisKeysUtil.getCaptchaConfigKey(uuid);
        if (code.equals(redisUtil.get(key))) {
            redisUtil.delete(key);
            return true;
        }
        return false;
    }

    /**
     * 生成验证码图片
     *
     * @return
     */
    private Map<String, Object> createCaptcha() {
        Map<String, Object> data = new HashMap<>();
        Map<String, String> result = new HashMap<>();
        String code = producer.createText();
        String uuid = UUIDUtil.randomUUID();
        redisUtil.set(RedisKeysUtil.getCaptchaConfigKey(uuid), code, 5 * 60);
        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String base64Img = null;
        try {
            ImageIO.write(image, "jpg", outputStream);
            base64Img = Base64.encodeBase64String(outputStream.toByteArray());
            outputStream.close();
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(base64Img);
            base64Img = m.replaceAll("");
        } catch (IOException e) {
            log.error("图片验证码生成出错", e.getStackTrace());
            result.put("msg", "图片验证码生成出错");
            data.put("data", result);
            return data;
        }
        result.put("uuid", uuid);
        result.put("base64", "data:image/png;base64," + base64Img);
        data.put("data", result);
        return data;
    }
}

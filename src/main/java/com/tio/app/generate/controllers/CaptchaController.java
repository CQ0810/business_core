package com.tio.app.generate.controllers;

import com.tio.app.common.services.CaptchaService;
import com.tio.app.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author likui
 * @create 2018-12-28
 **/

@RestController
@RequestMapping(value = "/generate/captcha")
public class CaptchaController {


    @Autowired
    private CaptchaService captchaService;

    /**
     * 获取验证码
     */
    @PostMapping("/get-captcha")
    public ResultUtil getCaptcha() {
        Map map = captchaService.getCaptcha();
        return ResultUtil.ok(map);
    }
}

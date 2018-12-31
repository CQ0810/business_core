package com.tio.app.sys.controllers.admin;

import com.tio.app.common.pojo.ResultCode;
import com.tio.app.common.services.CaptchaService;
import com.tio.app.common.utils.ResultUtil;
import com.tio.app.sys.services.LoginService;
import com.tio.app.sys.vo.request.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping(value = "/sys/admin/admin-cloud")
@RestController
@Slf4j
public class LoginController {

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private LoginService loginUser;

    /**
     * 获取验证码
     */
    @PostMapping("/get-captcha")
    public ResultUtil getCaptcha() {
        Map map = captchaService.getCaptcha();
        return ResultUtil.ok(map);
    }

    /**
     * admin 登录
     *
     * @param loginVO
     * @return
     */
    @PostMapping("/login")
    public ResultUtil login(@RequestBody LoginVO loginVO) {
        if (!captchaService.checkCaptcha(loginVO.getUuid(), loginVO.getCode())) {
            return ResultUtil.error(ResultCode.NEED_CAPTCHA);
        }
        try {
            ResultUtil re = loginUser.loginUser(loginVO);
            return re;
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResultUtil.error(ResultCode.LOGIN_ERROR);
        }
    }
}

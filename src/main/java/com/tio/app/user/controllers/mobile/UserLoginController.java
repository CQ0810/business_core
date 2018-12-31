package com.tio.app.user.controllers.mobile;

import com.tio.app.common.pojo.ResultCode;
import com.tio.app.common.services.CaptchaService;
import com.tio.app.common.services.SendSMSService;
import com.tio.app.common.utils.ResultUtil;
import com.tio.app.sys.vo.request.LoginVO;
import com.tio.app.user.entityes.UUser;
import com.tio.app.user.services.UUserService;
import com.tio.app.user.services.UserLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author likui
 * @create 2018-12-26
 * 用户登录
 **/
@RequestMapping(value = "/login/user")
@RestController
@Slf4j
public class UserLoginController {

    @Autowired
    private SendSMSService sendSMSService;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UUserService uUserService;

    /**
     * 获取验证码
     */
    @PostMapping("/get-captcha")
    public ResultUtil getCaptcha() {
        Map map = captchaService.getCaptcha();
        return ResultUtil.ok(map);
    }

    /**
     * 发送手机验证码
     * @param phone
     * @return
     */
    @GetMapping("/sendSMS")
    public ResultUtil getCaptcha(@RequestParam String phone) {
        return ResultUtil.ok(sendSMSService.send(phone));
    }

    /**
     * 登录
     * @param loginVO
     * @return
     */
    @PostMapping("/login")
    public ResultUtil login(@RequestBody LoginVO loginVO) {
        if (!captchaService.checkCaptcha(loginVO.getUuid(),loginVO.getCode())) {
            return ResultUtil.error(ResultCode.NEED_CAPTCHA);
        } else {
            try {
                ResultUtil re = userLoginService.loginUserName(loginVO);
                return re;
            } catch (Exception e) {
                log.error(e.getMessage());
                return ResultUtil.error(ResultCode.LOGIN_ERROR);
            }
        }
    }

    /**
     * 手机号登录
     * @param loginVO
     * @return
     */
    @PostMapping("/phone-login")
    public ResultUtil loginPhone(@RequestBody LoginVO loginVO) {
        int type=sendSMSService.verification(loginVO.getUserName(),loginVO.getCode());
        if(type==1){
            return userLoginService.loginPhone(loginVO);
        }else{
            return ResultUtil.error("验证码错误");
        }
    }


    /**
     * 添加用户表
     * @param user
     * @return
     */
    @PostMapping("/save-user")
    public ResultUtil saveUser(@RequestBody UUser user) {
        if(user.getId()==null){
            int type=sendSMSService.verification(user.getPhone(),user.getCode());
            if(type==1){
                return  uUserService.saveUser(user.getPhone(),user);
            }else{
                return ResultUtil.error("验证码错误");
            }
        }else{
            return uUserService.updateUser(user);
        }
    }
}

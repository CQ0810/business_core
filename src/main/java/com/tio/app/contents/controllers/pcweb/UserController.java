package com.tio.app.contents.controllers.pcweb;

import com.tio.app.common.pojo.HttpClientResult;
import com.tio.app.common.services.CaptchaService;
import com.tio.app.common.services.SendSMSService;
import com.tio.app.common.utils.*;
import com.tio.app.contents.entities.User;
import com.tio.app.contents.services.TestService;
import com.tio.app.contents.vo.TestVO;
import com.tio.app.contents.services.UserService;
import com.tio.app.sys.entities.SPermissions;
import com.tio.app.sys.services.SPermissionsService;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/pcweb/user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SPermissionsService SPermissionsService;
    @Autowired
    private TestService KKServiceImpl;

    @Autowired
    private SendSMSService netEaseSMSService;

    @PostMapping("/addUser")
    public int add(@Validated @RequestBody TestVO testVO) {
        return userService.add(testVO);
    }

    @GetMapping("/test")
    public void test() {
        System.out.println(KKServiceImpl.test());
    }

    @GetMapping("/list")
   // @RequiresPermissions("sys-aaa-bbb")
    public String list() {
        //Map<String, Object> captcha = captchaService.getCaptcha();
        /*List<SPermissions> permissions = SPermissionsService.getPermissionTree();
        System.out.println(permissions);*/
        userService.list();
        return "aaaa";
    }

    @PostMapping("/check")
    public ResultUtil checkCaptcha(@Param("uuid") String uuid, @Param("code") String code) throws Exception {
        Map<String, String> param = new HashMap<>();
        param.put("name", "jack");
        param.put("age", "23");
        HttpClientResult httpClientResult = HttpClientUtil.doGet("https://yc1.heal1x.com:8080/cloudHealth-yunwei/web/app.php", param);
        System.out.println(httpClientResult.getContent());
        return ResultUtil.ok(httpClientResult.getContent());
    }

    @PostMapping("/upload")
    public ResultUtil upload(@RequestParam("file") MultipartFile file) {
        String xx = UploadUtil.singleUpload(file, "e:\\test1\\");
        return ResultUtil.ok("单文件上传 : " + xx);
    }

    @PostMapping("/uploads")
    public ResultUtil upload(@RequestParam("files") MultipartFile[] files) {
        Set<String> xx = UploadUtil.multiUpload(files, "e:\\test1\\");
        return ResultUtil.ok("多文件上传 : " + xx);
    }

    @GetMapping("/qrcode")
    @RequiresRoles({"role1"})
    public ResultUtil qrcode() {
        String text = "http://www.baidu.com";
        //不含Logo
        //QRCodeUtil.encode(text, null, "e:\\", true);
        //含Logo，不指定二维码图片名
        //QRCodeUtil.encode(text, "e:\\csdn.jpg", "e:\\", true);
        //含Logo，指定二维码图片名
        try {
            String str = QRCodeUtil.encode(text, "e:\\csdn.jpg", "e:\\", "qrcode", true);
            System.out.printf("str");
            return ResultUtil.ok(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error("生成二维码错误");
    }

    @GetMapping("/decode_qrcode")
    public ResultUtil decodeQrCode() throws Exception {
        String decode = QRCodeUtil.decode("e:\\qrcode.jpg");
        return ResultUtil.ok(decode);
    }

    @GetMapping("/random_str")
    public ResultUtil randomStr() {
        String string = RandomStrUtils.getInstance().getRandomString(RandomStrUtils.STR_TYPE, 4);
        return ResultUtil.ok(string);
    }

    @GetMapping("/send_sms")
    public ResultUtil sendSMS() {
        Map<String, Object> send = netEaseSMSService.send("15882215295");
        return ResultUtil.ok(send);
    }

    @GetMapping("/check_sms")
    public ResultUtil checkSMS(@RequestParam("phone") String phone, @RequestParam("code") String code) {
        int verification = netEaseSMSService.verification(phone, code);
        return ResultUtil.ok("ok : " + verification);
    }
}

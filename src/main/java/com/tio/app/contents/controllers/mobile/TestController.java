package com.tio.app.contents.controllers.mobile;

import com.alibaba.fastjson.JSON;
import com.tio.app.common.utils.AuthCodeUtil;
import com.tio.app.common.utils.SignatureUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/test")
    public String test(@RequestParam Map<String, String> map) throws Exception {
        String sign = SignatureUtil.generateSignature(map, "hello");
        System.out.println(map);
        if (sign.equals(map.get("sign"))) {
            return "数据签名正确";
        }
        return "数据签名错误";
    }

    @PostMapping("/testPost001")
    public String testPost(HttpServletRequest request) {
        System.out.println(request.getParameterMap().entrySet());
        return "Test Post";
    }

    @GetMapping("/xx")
    public String decryptData(@RequestParam Map<String, String> map) throws Exception {
        System.out.println(map);
        String param = map.get("param");
        String str = AuthCodeUtil.authCodeDecode(param, "123456");
        Map<String, String> mapData = JSON.parseObject(str, Map.class);
        System.out.println(mapData.get("age") + " " + mapData.get("name"));
        /*String sign = SignatureUtil.generateSignature(mapData, "hello");
        System.out.println(sign);
        System.out.println(map.get("sign"));*/
        return mapData.get("age") + " " + mapData.get("name");
    }
    
    @GetMapping("/testCors")
    public String testCors() {
        return "Test CORS";
    }
}

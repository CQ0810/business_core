package com.tio.app.wechat.controllers.mobile;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMenuService;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
public class WxMenuController {
    @Autowired
    private WxMpMenuService wxMpMenuService;

    @GetMapping("/menus")
    public String menus() throws WxErrorException {
        WxMpMenu wxMpMenu = wxMpMenuService.menuGet();
        return "success";
    }
}

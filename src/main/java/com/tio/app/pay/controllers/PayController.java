package com.tio.app.pay.controllers;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.tio.app.common.utils.IPUtil;
import com.tio.app.common.utils.ResultUtil;
import com.tio.app.common.utils.UUIDUtil;
import com.tio.app.pay.pojo.WeChatPay;
import com.tio.app.pay.services.WechatPayService;
import com.tio.app.pay.utils.WechatUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/pay")
@Slf4j
public class PayController {
    @Autowired
    private WechatPayService wechatPayService;

    @PostMapping("/wechat-pay")
    public ResultUtil wechatPay(@RequestParam("sign") String sign, @RequestParam("param") String param) {
        WeChatPay weChatPay = WechatUtils.decodeToken(param);
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        request.setOutTradeNo(weChatPay.getOutTradeNo());
        String nonceStr = UUIDUtil.randomUUID();
        request.setNonceStr(nonceStr);
        request.setTotalFee(weChatPay.getTotalFee());
        request.setSpbillCreateIp(IPUtil.getIpAddr(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()));
        request.setBody(weChatPay.getBody());
        request.setOpenid(weChatPay.getOpenid());
        request.setNotifyUrl(weChatPay.getNotifyUrl());
        WxPayMpOrderResult result = null;
        try {
            result = wechatPayService.getWxPayService().createOrder(request);
        } catch (WxPayException e) {
            log.error("微信生成订单失败", e.getStackTrace());
        }
        return ResultUtil.ok().put("data", result);
    }

    @PostMapping("/wechat-notify")
    public String getWeChatNotify(@RequestParam("xmlData") String xmlData) {
        WxPayOrderNotifyResult result = new WxPayOrderNotifyResult();
        try {
            result = wechatPayService.getWxPayService().parseOrderNotifyResult(xmlData);
        } catch (WxPayException e) {
            log.error("微信回调失败", e.getStackTrace());
        }
        return result.getXmlString();
    }
}

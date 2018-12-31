package com.tio.app.pay.services.impl;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.tio.app.pay.config.WeChatConstant;
import com.tio.app.pay.services.WechatPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class WechatPayServiceImpl implements WechatPayService {

    @Autowired
    private WeChatConstant weChatConstant;


    @Override
    public WxPayService getWxPayService() {
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(wxPayConfig());
        return wxPayService;
    }

    private WxPayConfig wxPayConfig() {
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId(weChatConstant.getAppId());
        wxPayConfig.setMchId(weChatConstant.getMchId());
        wxPayConfig.setTradeType(WxPayConstants.TradeType.JSAPI);
        wxPayConfig.setMchKey(weChatConstant.getMchKey());
        wxPayConfig.setSignType(WxPayConstants.SignType.MD5);
        wxPayConfig.setNotifyUrl(weChatConstant.getNotifyUrl());
        wxPayConfig.setKeyPath(weChatConstant.getKeyPath());
        wxPayConfig.setKeyPath(weChatConstant.getCertRootPath() + File.separator + weChatConstant.getCertLocalPath());
        return wxPayConfig;
    }
}

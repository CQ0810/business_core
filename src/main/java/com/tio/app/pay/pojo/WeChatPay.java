package com.tio.app.pay.pojo;

import lombok.Data;

@Data
public class WeChatPay {

	private String outTradeNo;
	
	private Integer totalFee;
	
	private String body;
	
	private String openid;
	
	private String notifyUrl;
}
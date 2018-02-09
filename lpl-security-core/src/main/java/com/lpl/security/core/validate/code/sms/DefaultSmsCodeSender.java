package com.lpl.security.core.validate.code.sms;

public class DefaultSmsCodeSender implements SmsCodeSender {

	@Override
	public void send(String mobile, String code) {

		System.out.println("手机号："+mobile +",验证码："+code);
	}

}

package com.lpl.security.core.validate.code.sms;

import java.time.LocalDateTime;

public class SmsCode  {

	private String code;
	
	private LocalDateTime expireTime;
	
	public SmsCode(String code, LocalDateTime expireTime) {
		this.code = code;
		this.expireTime = expireTime;
	}
	
	public SmsCode(String code,int expireIn) {
		this.code = code;
		this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDateTime getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(LocalDateTime expireTime) {
		this.expireTime = expireTime;
	}

	public boolean isExpried() {
		// TODO Auto-generated method stub
		return LocalDateTime.now().isAfter(expireTime);
	}
}

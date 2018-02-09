package com.lpl.security.core.properties;
/**
 * 
 * @author lpl
 *
 */
public class ValidateCodeProperties {

	private ImageCodeProperties image = new ImageCodeProperties();
	
	private SmsCodeProperties smsCode = new SmsCodeProperties();

	public ImageCodeProperties getImage() {
		return image;
	}

	public void setImage(ImageCodeProperties image) {
		this.image = image;
	}

	public SmsCodeProperties getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(SmsCodeProperties smsCode) {
		this.smsCode = smsCode;
	}
	
	
	
}

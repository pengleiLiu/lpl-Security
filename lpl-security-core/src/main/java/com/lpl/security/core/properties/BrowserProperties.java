package com.lpl.security.core.properties;

public class BrowserProperties {

	private String signUpUrl = "/imooc-signUp.html";
	
	private String loginPage = "/standard-login.html";
	
	private LoginResponseType loginType = LoginResponseType.JSON;

	private int rememberMeSecond = 3600;
	
	
	public String getSignUpUrl() {
		return signUpUrl;
	}

	public void setSignUpUrl(String signUpUrl) {
		this.signUpUrl = signUpUrl;
	}

	public LoginResponseType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginResponseType loginType) {
		this.loginType = loginType;
	}

	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	public int getRememberMeSecond() {
		return rememberMeSecond;
	}

	public void setRememberMeSecond(int rememberMeSecond) {
		this.rememberMeSecond = rememberMeSecond;
	}
	
	
	
}

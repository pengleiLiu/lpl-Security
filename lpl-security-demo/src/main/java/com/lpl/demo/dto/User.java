package com.lpl.demo.dto;

import java.util.Date;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

import com.lpl.demo.validator.MyConstraint;

/**
 * 
 * @author lpl
 *
 */
public class User {

	private String id;
	
	/**
	 * @MyConstraint
	 * 是自定义的一个验证注解
	 */
	@MyConstraint(message="这是一个测试")
	private String username;
	
	@NotBlank(message = "密码不能为空")
	private String password;

	@Past(message = "生日必须是过去时间")
	private Date birthday;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
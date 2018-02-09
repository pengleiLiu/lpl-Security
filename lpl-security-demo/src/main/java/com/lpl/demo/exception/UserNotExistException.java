package com.lpl.demo.exception;
/**
 * 
 * @author lpl
 *
 */
public class UserNotExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	public UserNotExistException(String id) {
		super("user is not exist");
		this.id  = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}

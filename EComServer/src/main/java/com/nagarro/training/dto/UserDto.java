package com.nagarro.training.dto;

import javax.validation.constraints.NotEmpty;

public class UserDto {
	private int uid;
	
	@NotEmpty
	private String username;
	
	@NotEmpty
	private String password;
	
	private String uname;

	public UserDto() {

	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

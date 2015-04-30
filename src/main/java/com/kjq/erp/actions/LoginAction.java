package com.kjq.erp.actions;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

public class LoginAction {
	
	private String  login_error;
	
	@Action(value="login",results={@Result(name="success",location="login.html")})
	public String login(){
		return "success";
	}

	public String getLogin_error() {
		return login_error;
	}

	public void setLogin_error(String login_error) {
		this.login_error = login_error;
	}
}

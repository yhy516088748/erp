package com.kjq.erp.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

public class Response {
	
	public void doResponse(String str) throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate,"); // HTTP
																					// 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setDateHeader("Expires", 0); // Proxies
		
		response.getWriter().write(str);
	}
}

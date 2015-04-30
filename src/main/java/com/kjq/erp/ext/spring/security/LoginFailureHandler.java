package com.kjq.erp.ext.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.TextEscapeUtils;
/**
 * 自定义login failure handler
 * @author York
 *
 */
public class LoginFailureHandler implements AuthenticationFailureHandler{
	
	public static final String SPRING_SECURITY_LAST_USERNAME_KEY = "SPRING_SECURITY_LAST_USERNAME";

	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		
		
		String username = exception.getAuthentication().getName();
		
		HttpSession session = request.getSession(false);
        if (session != null) {
            request.getSession().setAttribute(SPRING_SECURITY_LAST_USERNAME_KEY, TextEscapeUtils.escapeEntities(username));
        }
		
		if(exception instanceof UsernameNotFoundException){
			response.sendRedirect("login.do?login_error=1");
		}
		if(exception instanceof BadCredentialsException){
			response.sendRedirect("login.do?login_error=2");
		}
		
		
	}

}

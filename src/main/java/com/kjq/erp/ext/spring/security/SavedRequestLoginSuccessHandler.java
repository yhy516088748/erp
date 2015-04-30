package com.kjq.erp.ext.spring.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

/**
 * 
 * 
 * <beans:bean id="loginSuccessHandler" class="com.kjq.ims.ext.spring.security.SavedRequestLoginSuccessHandler"> 
 * 		<beans:property name="alwaysUseDefaultTargetUrl" value="true"/>
 * </beans:bean>
 * 
 * alwaysUseDefaultTargetUrl = false时，返回到访问拒绝的页面
 * form-login 节点的always-use-default-target属性配置在自定义 authentication-success-handler-ref 的情况下无效
 * 
 * @author York
 *
 */
public class SavedRequestLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	private RequestCache requestCache = new HttpSessionRequestCache();
	private boolean changeRole = false;
	private String changeRoleUrl;
	
	
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication auth) throws IOException,
			ServletException {
		
		if(isChangeRole()){
        	getRedirectStrategy().sendRedirect(request, response, getChangeRoleUrl());
        	return;
        }
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest == null) {
        	super.onAuthenticationSuccess(request, response, auth);

            return;
        }
        
        if (isAlwaysUseDefaultTargetUrl() || StringUtils.hasText(request.getParameter(getTargetUrlParameter()))) {
            requestCache.removeRequest(request, response);
            super.onAuthenticationSuccess(request, response, auth);
            return;
        }
		
        clearAuthenticationAttributes(request);

        // Use the DefaultSavedRequest URL
        String targetUrl = savedRequest.getRedirectUrl();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

	public boolean isChangeRole() {
		return changeRole;
	}

	public void setChangeRole(boolean changeRole) {
		this.changeRole = changeRole;
	}

	public String getChangeRoleUrl() {
		return changeRoleUrl;
	}

	public void setChangeRoleUrl(String changeRoleUrl) {
		this.changeRoleUrl = changeRoleUrl;
	}

}

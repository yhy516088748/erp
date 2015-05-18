package com.kjq.erp.ext.spring.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 * 
 * 启用资源(如url)过滤
 * 
 * 自定义filter interceptor
 * <custom-filter ref="customFilter" before="FILTER_SECURITY_INTERCEPTOR" />
 * 相关类   com.kjq.ims.ext.spring.security.CustomSecurityMetadataSource
 *        com.kjq.ims.ext.spring.security.CustomAccessDecisionManager 
 * 
 * @see http://docs.spring.io/spring-security/site/docs/3.1.4.RELEASE/reference/ns-config.html#ns-custom-filters
 *      http://docs.spring.io/spring-security/site/docs/3.1.4.RELEASE/reference/authz-arch.html#authz-role-voter
 *      http://fantasyeye.iteye.com/blog/1938046
 * @author York
 *
 */
public class CustomFilter extends AbstractSecurityInterceptor
		implements Filter {
	
	private FilterInvocationSecurityMetadataSource securityMetadataSource;
	private boolean observeOncePerRequest = true;
	private static final String FILTER_APPLIED = "__spring_security_filterSecurityInterceptor_filterApplied";
	/**
	 * @param filterConfig
	 * @throws ServletException
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	/**
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		invoke(fi);
	}
	
	
	public void invoke(FilterInvocation fi) throws IOException, ServletException{
		/**
		InterceptorStatusToken token = super.beforeInvocation(fi);
		
		try {
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} catch (Exception e) {
			super.afterInvocation(token, null);
		}
		*/
		if ((fi.getRequest() != null) && (fi.getRequest().getAttribute(FILTER_APPLIED) != null)
                && observeOncePerRequest) {
            // filter already applied to this request and user wants us to observe
            // once-per-request handling, so don't re-do security checking
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } else {
            // first time this request being called, so perform security checking
            if (fi.getRequest() != null) {
                fi.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
            }

            InterceptorStatusToken token = super.beforeInvocation(fi);

            try {
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            } finally {
                super.finallyInvocation(token);
            }

            super.afterInvocation(token, null);
        }
		
	}


	/**
	 * 
	 */
	public void destroy() {
	}

	/**
	 * @return
	 */
	@Override
	public Class<? extends Object> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	/**
	 * @return
	 */
	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}

}

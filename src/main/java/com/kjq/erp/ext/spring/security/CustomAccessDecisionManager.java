package com.kjq.erp.ext.spring.security;


import java.util.Collection;
import java.util.Iterator;
import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * @author York
 *
 */
public class CustomAccessDecisionManager implements AccessDecisionManager {

	/**
     * LOGGER 日志对象
     */
    private final static Logger LOGGER = Logger.getLogger(CustomAccessDecisionManager.class);
	
	/**
	 * @param authentication
	 * @param object
	 * @param configAttributes
	 * @throws AccessDeniedException
	 * @throws InsufficientAuthenticationException
	 */
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		
		LOGGER.info("CustomAccessDecisionManager.decide");

		if (null == configAttributes || configAttributes.size() <= 0) {
			return;
		}
		
		ConfigAttribute c = null;
		String needRole = null;
		for (Iterator<ConfigAttribute> iter = configAttributes.iterator(); iter.hasNext(); ) {
			c = iter.next();
			
			needRole = c.getAttribute();
			
			LOGGER.info("菜单访问权限：" + needRole);
			for (GrantedAuthority ga : authentication.getAuthorities()) {
				if (needRole.trim().equals(ga.getAuthority())) {
					return;
				}
			}
		}
		
		throw new AccessDeniedException("结束，没有权限！");
	}

	/**
	 * @param attribute
	 * @return
	 */
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	/**
	 * @param clazz
	 * @return
	 */
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
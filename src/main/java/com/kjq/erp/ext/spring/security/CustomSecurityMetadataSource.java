package com.kjq.erp.ext.spring.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;

/**
 * 
 * @author York
 *
 */
public class CustomSecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {

    private final static Logger LOGGER = Logger.getLogger(CustomSecurityMetadataSource.class);
    
    private HashMap<String, Collection<ConfigAttribute>> map = new HashMap<String, Collection<ConfigAttribute>>();
    
	/** 
	 * 加载资源，初始化资源变量
	 * 
	 */
	private void loadResourceDefine() {
		
		Collection<ConfigAttribute> array = new ArrayList<ConfigAttribute>(4);
		
		ConfigAttribute cfg = new SecurityConfig("a1");
		array.add(cfg);
		
		cfg = new SecurityConfig("a2");
		array.add(cfg);
		
		cfg = new SecurityConfig("a3");
		array.add(cfg);
		
		cfg = new SecurityConfig("a4");
		array.add(cfg);
		map.put("/demo/list", array);
		
		array = new ArrayList<ConfigAttribute>(4);
		
		cfg = new SecurityConfig("n1");
		array.add(cfg);
		
		cfg = new SecurityConfig("n2");
		array.add(cfg);
		map.put("/demo/news", array);
		
	}
	
	
	public CustomSecurityMetadataSource() {
		loadResourceDefine();
	}
	
	/**
	 * 根据路径获取访问权限的集合接口
	 * @param object
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		
		LOGGER.info(object);
		
		HttpServletRequest request = ((FilterInvocation)object).getHttpRequest();
		
		RequestMatcher matcher = null;
		String resUrl = null;
		for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
			resUrl = iter.next();
			matcher = new AntPathRequestMatcher(resUrl);
			if (null != resUrl && matcher.matches(request)) {
				return map.get(resUrl);
			}
		}
		
		return null;
	}

	/**
	 * @return
	 */
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	/**
	 * @param clazz
	 * @return
	 */
	public boolean supports(Class<?> clazz) {
		return true;
	}

}


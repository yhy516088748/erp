package com.kjq.erp.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
/**
 * <p>
 * 类名称: [配置文件工具类]
 * </p>
 * <p>
 * 类描述: [用于读取property文件]
 * </p>
 * 
 * @author franklin
 * @version 1.0
 */
public class PropertyUtils {
	private static Properties props = null;

	private static void init() {
		Resource resource = new ClassPathResource("system.properties");
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getStringValByKey(String key) {
		if (props == null) {
			init();
		}
		if (props != null) {
			return props.getProperty(key);
		}
		return null;
	}

	public static Double getDoulbeValByKey(String key) {
		if (props == null) {
			init();
		}
		if (props != null) {
			String val = props.getProperty(key);
			if (val != null && val.length() > 0) {
				return Double.parseDouble(val);
			}
		}
		return null;
	}

	public static Integer getIntegerValByKey(String key) {
		if (props == null) {
			init();
		}
		if (props != null) {
			String val = props.getProperty(key);
			if (val != null && val.length() > 0) {
				return Integer.parseInt(val);
			}
		}
		return null;
	}
}

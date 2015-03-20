package com.yz.snews.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext context;
	
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		SpringContextHolder.context = context;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		return (T)context.getBean(beanName);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName, Object... args) {
		return (T)context.getBean(beanName, args);
	}
	
	public static <T> T getBean(Class<T> classType) {
		return context.getBean(classType);
	}
}

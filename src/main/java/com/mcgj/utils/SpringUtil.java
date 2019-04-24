package com.mcgj.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

public class SpringUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		SpringUtil.applicationContext = context;
	}

	public static <T> Object getBean(String name) {
		return applicationContext.getBean(name);
	}
	public static <T> Object getBean(Class<T> cls) {
		return applicationContext.getBean(cls);
	}
	public static boolean isPrototype(String beanName) {
		return applicationContext.isPrototype(beanName);
	}
	public static boolean isSingleton(String beanName) {
		return applicationContext.isSingleton(beanName);
	}
	/**
	 * 获取指定类的所有bean实例
	 * 
	 * @param cls
	 * @return
	 */
	public static <T> List<T> getBeans(Class<T> cls) {
		Map<String, T> matchingBeans = BeanFactoryUtils
				.beansOfTypeIncludingAncestors(applicationContext, cls, true,
						false);
		return new ArrayList<T>(matchingBeans.values());
	}
	/**
	 * 获取指定类的所有bean实例
	 * 
	 * @param cls
	 * @return
	 */
	public static <T> Map<String, T> getBeansMap(Class<T> cls){
		Map<String, T> matchingBeans = BeanFactoryUtils
				.beansOfTypeIncludingAncestors(applicationContext, cls, true,
						false);
		return matchingBeans;
	}
}
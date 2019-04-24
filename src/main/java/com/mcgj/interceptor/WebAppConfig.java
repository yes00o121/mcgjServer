package com.mcgj.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 配置类,项目启动会执行,可以添加系统的拦截器
 * @author Administrator
 *
 */
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter{
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		super.addInterceptors(registry);
		registry.addInterceptor(new PermissionInterceptor()).addPathPatterns("/**");
	}

}

package com.mcgj.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * 安全过滤器,用户发送请求时做一些安全的过滤
 * @author 杨晨
 * @date 2019-03-28
 * @address 海口
 *
 */
@WebFilter(filterName="secuityFilter",urlPatterns = "/*")
public class SecurityFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}

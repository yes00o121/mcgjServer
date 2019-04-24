package com.mcgj.web.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
@WebFilter(filterName="mcgjFilter",urlPatterns = "/*")
public class MCGJFilter implements Filter {
	
	private Logger log = Logger.getLogger(MCGJFilter.class);

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain domain)
			throws IOException, ServletException {
		try {
			res.setCharacterEncoding("utf-8");

			res.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8");
			HttpServletResponse response = (HttpServletResponse) res;
			HttpServletRequest request = (HttpServletRequest) req;
			String uri = request.getRequestURI();
			Map<String, String> params = getRequestParamMap(request);
			if (!params.isEmpty()) {
				uri = uri + "?";
			}
			Set<Entry<String, String>> paramsSet = params.entrySet();
			// 拼接用户请求
			for (Entry<String, String> entry : paramsSet) {
				String paramName = entry.getKey();
				String paramValue = entry.getValue();
				uri = uri + "&" + paramName + "=" + paramValue;
			}
			System.out.println(uri);
			// 响应头设置,添加跨域访问权限
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Credentials", "true");
			domain.doFilter(request, response);
		} catch (Exception e) {
			log.error(e);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

	private final Map<String, String> getRequestParamMap(HttpServletRequest request) {
		Map<String, String> requestParamsMap = new HashMap<String, String>();
		try {
			Enumeration<String> e = request.getParameterNames();
			while (e.hasMoreElements()) {
				String param = e.nextElement();
				String value = request.getParameter(param);
				if (value != null) {
					requestParamsMap.put(param, value);
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
		return requestParamsMap;
	}
}

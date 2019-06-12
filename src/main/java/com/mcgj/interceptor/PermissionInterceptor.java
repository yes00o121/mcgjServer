package com.mcgj.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.mcgj.entity.User;
import com.mcgj.redis.RedisHashUtil;
import com.mcgj.utils.MessageUtil;
import com.mcgj.utils.PropertiesUtil;
import com.mcgj.web.dto.ResultDTO;

/**
 * 拦截器
 * 
 * @author 杨晨
 *
 */
public class PermissionInterceptor implements HandlerInterceptor {

	private static final Logger log = LoggerFactory
			.getLogger(PermissionInterceptor.class);

	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) throws Exception {
		//收到请求首先判断用户是否登录，如果登录就更新其会话可以知道用户是否活跃，不然不做操作
		updateConversation(request);
		// 如果是不拦截接口直接通过
		String url = request.getServletPath();
		if (url.indexOf("select") != -1) {// 如果为查询接口不进行拦截
			return true;
		} else if ("/user/login".equals(url)) {// 登录接口
			return true;
		} else if ("/user/register".equals(url)) {// 注册接口
			return true;
		}/* else if ("/common/uploadFile".equals(url)) {// 上传文件接口
			return true;
		} else if ("/common/image".equals(url)) {// 查询图片接口
			return true;
		} else if ("/common/generateVerification".equals(url)) {// 验证码接口
			return true;
		}else if ("/common/upNetWorkImg".equals(url)) {// 上传网络图片
			return true;
		}*/
		//common下的请求不拦截
		else if(url.startsWith("/common/")){
			return true;
		}else if("/systemConfig/updateSystemConfigCache".equals(url)){//刷新配置缓存
			return true;
		}
		//如果参数中的爬虫key和配置中的一致,给予通过
		String token = request.getParameter("spiderToken");
		if(token != null){
			String spiderKey = PropertiesUtil.get("delay.properties", "spider_key");
			if(token.equals(spiderKey)){
				return true;
			}
		}
		//除以上接口之外其他接口都需要用户登录后才能进行操作
		return loginLegitimate(request, response);// 拦截用户的登录状态
//		return true;
	}

	/**
	 * 判断用户登录是否合法
	 * 
	 * @return
	 */
	private Boolean loginLegitimate(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=UTF-8");  
			String token = request.getParameter("token");
			if (token == null || "".equals(token)) {
				sendLoginMessage(new PrintWriter(response.getOutputStream()));// 返回错误消息
				return false;
			}
			// 判断用户会话是否存在或者过期
			Object record = RedisHashUtil.get(token);
			if (record == null) {
				sendOverTimeMessage(new PrintWriter(response.getOutputStream()));
				return false;
			}
			// 用户状态正常更新会话时长
			RedisHashUtil.setex(token, (User) record,
					PropertiesUtil.getLoginDelay());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return false;
	}

	/**
	 * 用户没有登录错误消息
	 * 
	 * @return
	 */
	private void sendLoginMessage(PrintWriter pw) {
		try{
		ResultDTO result = new ResultDTO();
		result.setSuccess(false);
		result.setState(0);// 状态为false
		result.setMessage(new String(MessageUtil.MSG_NOT_LOGIN.getBytes("utf-8")));
		pw.append(JSONObject.toJSONString(result));
		pw.flush();
		pw.close();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	/**
	 * 会话过期错误消息
	 * 
	 * @return
	 */
	private void sendOverTimeMessage(PrintWriter pw) {
		try{
			ResultDTO result = new ResultDTO();
			result.setSuccess(false);
			result.setState(2);// 状态为false
			result.setMessage(new String(MessageUtil.MSG_CONVERSATION_OVERTIME.getBytes("utf-8")));
			pw.append(JSONObject.toJSONString(result));
			pw.flush();
			pw.close();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
	//判断用户是否登录，如果登录更新会话，不然不做操作
	private void updateConversation(HttpServletRequest request){
		//获取token
		String token = request.getParameter("token");
		if(token != null){//用于有登录过
			//判断用户缓存是否存在，不存在不进行操作，存在进行更新
			Object record = RedisHashUtil.get(token);
			if(record != null){
				RedisHashUtil.setex(token, (User) record,
						PropertiesUtil.getLoginDelay());
			}
		}
	}
}

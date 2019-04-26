package com.mcgj.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.PathParam;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mcgj.entity.User;
import com.mcgj.redis.RedisHashUtil;
import com.mcgj.service.IUserService;
import com.mcgj.utils.CommonUtil;
import com.mcgj.utils.MessageUtil;
import com.mcgj.web.dto.ResultDTO;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractBaseController {
	/**
	 * 记录日志
	 */
	Logger log = Logger.getLogger(UserController.class);

	@Autowired
	private IUserService userService;
	
	/**
	 * 用户登录方法 由于跨域请求，每次获取的sessionId都不同，所以使用token进行用户判断
	 * 
	 * @param res
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/login",method={RequestMethod.POST})
	@ResponseBody
	public ResultDTO login(HttpServletResponse response,
			HttpServletRequest request, User user, HttpSession session,
			@PathParam("userName") String userName) {
		try {
			//获取用户浏览器和操作系统
			String[] osAndBrowserInfo = CommonUtil.getOsAndBrowserInfo(request);
			user.setOs(osAndBrowserInfo[0]);//操作系统
			user.setBrowser(osAndBrowserInfo[1]);//浏览器
			user.setIp(request.getLocalAddr());//获取ip
			User record = this.userService.login(user);
			return new ResultDTO(MessageUtil.MSG_LOGIN_SUCCESS,true,record);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			return new ResultDTO(e.getMessage(),false,null);
		}
	}

	/**
	 * 用户注册方法
	 * @return
	 */
	@RequestMapping(value = "/register",method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO register(HttpServletRequest request,
			HttpServletResponse response, User user) {
		ResultDTO result = new ResultDTO();
		try {
			userService.register(user);
			result.setMessage(MessageUtil.MSG_REGISTER_SUCCESS);
			result.setResult(user);
			result.setSuccess(true);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			return result;
		}
	}
	/**
	 * 根据用户id查询用户未读的消息数据数量
	 * @return
	 */
	@RequestMapping("/selectUserUnreadMessageCountByUserId")
	@ResponseBody
	public ResultDTO selectUserUnreadMessageCountByUserId(Integer userId){
		ResultDTO result = new ResultDTO();
		try {
//			userService.register(user);
			result.setResult(userService.selectUserUnreadMessageCountByUserId(userId));
			result.setMessage(MessageUtil.MSG_QUERY_SUCCESS);
			result.setSuccess(true);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			return result;
		}
	}
	
	/**
	 * 判断用户token是否有效
	 * @return
	 */
	@RequestMapping(value="/selectUserIsEffective")
	@ResponseBody
	public ResultDTO selectUserIsEffective(@RequestParam("token") String token){
		Object user = RedisHashUtil.get(token);
		if(user != null){
			return new ResultDTO(MessageUtil.MSG_QUERY_SUCCESS, true, null);
		}else{
			return new ResultDTO(MessageUtil.MSG_QUERY_ERROR, false, null);
		}
	}
	
	/**
	 * 根据用户id其收藏的帖子数据
	 * @return
	 */
	@RequestMapping("/selectCollectionConversationChildByUserId")
	@ResponseBody
	public ResultDTO selectCollectionConversationChildByUserId(Integer userId){
		ResultDTO result = new ResultDTO();
		try {
			result.setResult(userService.selectCollectionConversationChildByUserId(userId));
			result.setMessage(MessageUtil.MSG_QUERY_SUCCESS);
			result.setSuccess(true);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			return result;
		}
	}
}

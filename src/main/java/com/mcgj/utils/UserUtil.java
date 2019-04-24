package com.mcgj.utils;

import com.mcgj.entity.User;
import com.mcgj.redis.RedisHashUtil;

/**
 * 用户工具类
 * @author ad
 *
 */
public class UserUtil {
	
	/**
	 * 获取登录用户对象
	 * @return
	 */
	public static User getCurrentUser(String token){
		Object user = RedisHashUtil.get(token);
		if(user == null){
			return null;
		}
		return (User)user;
	}
	
}

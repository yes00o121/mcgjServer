package com.mcgj.dao;

import org.apache.ibatis.annotations.Param;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.User;
import com.mcgj.entity.loginLog;
/**
 * 用户接口
 * @author ad
 *
 */
public interface UserMapper extends BaseService<User,Integer>{
	
	/**
	 * 登录接口
	 * @param user
	 * @return
	 */
	User login(User user);
	
	/**
	 * 根据用户账号查询用户数据
	 * @param account
	 * @return
	 */
	User findUserByAccount(@Param("account") String account);
	
	/**
	 * 获取用户未查看回复数据的数量
	 * @return
	 */
	Integer selectUserUnreadMessageCountByUserId(Integer userId);
	
	/**
	 * 记录用户登录日志
	 * @param loginLog
	 */
	void insertLoginLog(loginLog loginLog);
	
	/**
	 * 爬虫****
	 * 判断用户是否存在，不存在进行创建，然后返回用户信息
	 * @param userName
	 * @return
	 */
	Integer selectIsExists(@Param("userName")String userName);
	
}

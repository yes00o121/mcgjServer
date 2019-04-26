package com.mcgj.service;

import java.util.List;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.ConversationChild;
import com.mcgj.entity.User;
import com.mcgj.web.websocket.Message;

public interface IUserService extends BaseService<User,Integer>{
	/**
	 * 登录接口
	 * @return
	 */
	User login(User user);
	/**
	 * 注册方法
	 * @param user 用户实体类
	 * @return
	 */
	void register(User user);
	
	/**
	 * 根据用户账号查询用户数据
	 * @param account
	 * @return
	 */
	User findUserByAccount(String account);
	
	/**
	 *  根据用户id查询用户未读的消息数据数量
	 * @param userId
	 * @return
	 */
	List<Message> selectUserUnreadMessageCountByUserId(Integer userId);
	
	/**
	 * 根据用户id其收藏的帖子数据
	 * @return
	 */
	List<ConversationChild> selectCollectionConversationChildByUserId(Integer userId);
	
	/**
	 * 根据用户名称查询用户id
	 * @param userName
	 * @return
	 */
	public Integer selectUserIdByName(String userName);
}

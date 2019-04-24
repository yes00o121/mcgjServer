package com.mcgj.dao;

import java.util.List;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.UserFollowConversation;

public interface UserFollowConversationMapper extends BaseService<UserFollowConversation,Integer>{
	/**
	 * 添加用户关注的贴吧
	 * @param userFollowConversation
	 * @return
	 */
	void addConversationFollow(UserFollowConversation userFollowConversation);
	
	/**
	 * 取消用户关注的贴吧
	 * @param userFollowConversation
	 * @return
	 */
	void deleteConversationFollow(UserFollowConversation userFollowConversation);
	
	/**
	 * 查询用户关注的贴吧
	 * @param userFollowConversation
	 * @return
	 */
	List<UserFollowConversation> selectConversationFollow(UserFollowConversation userFollowConversation);
}

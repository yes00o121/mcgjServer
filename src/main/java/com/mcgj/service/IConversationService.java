package com.mcgj.service;

import java.util.List;
import java.util.Map;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.Conversation;
import com.mcgj.entity.User;
import com.mcgj.entity.UserFollowConversation;

public interface IConversationService extends BaseService<Conversation,Integer>{
	
	/**
	 * 查询最新的贴吧帖子数据(用户最后回复的帖子数据)
	 * @return
	 */
	List<Map<String,Object>> selectNewestConversation();
	
	/**
	 * 添加新的贴吧数据
	 * @return
	 */
	void addConversation(Conversation conversation);
	
	/**
	 * 查询当前帖子的标题头像等内容
	 * @param id
	 * @return
	 */
	Conversation selectConversationById(Conversation conversation);
	
	/**
	 * 根据名称查询贴吧数据
	 * @param id
	 * @return
	 */
	Conversation selectConversationByName(Conversation conversation);
	
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
	
	/**
	 * 修改贴吧数据
	 * @param conversation
	 */
	void updateConversation(Conversation conversation);
	
	/**
	 * 查询最新的贴吧帖子数据(用户最后回复的帖子数据)
	 * @return
	 */
	Map<String,Object> selectConversationStatistics(Conversation conversation);
	
	/**
	 * 查询贴吧数据，按用户关注量查询指定数量的贴吧数据
	 * @param limit 数量
	 * @return
	 */
	List<Conversation> selectMaxFollow(Integer limit);
	
	/**
	 * 查询贴吧的类型以后类型下对应的贴吧数据
	 * @return
	 */
	Map<String,List<Object>> selectConversationTypeAndData();
	
	/**
	 * 根据用户id查询用户关注的贴吧下的贴子最新动态
	 * @param user
	 * @return
	 */
	List<Map<String,Object>> selectUserFollowConversation(User user);
	
	/**
	 * 查询吧主相关数据
	 * @param conversation
	 * @return
	 */
	Map<String,Object> selectConversationMaster(Conversation conversation);
}

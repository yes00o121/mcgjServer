package com.mcgj.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.ConversationChild;
import com.mcgj.entity.User;
import com.mcgj.entity.UserCollectionConversationChild;

public interface ConversationChildMapper extends BaseService<ConversationChild,Integer>{
	
	/**
	 * 根据贴子id查询,获取当前帖子的第一层数据
	 * @return
	 */
	public Map<String,Object> selectConversationChildById(Integer id);
	
	/**
	 * 根据贴吧id查询帖子
	 * @return
	 */
	public List<ConversationChild> selectConversationChildByConversationId(ConversationChild conversationChild);
	
	/**
	 * 新增帖子的方法
	 * @param conversationChild
	 */
	public void insert(ConversationChild conversationChild);
	
	
	/**
	 * 根据贴子的id获取用户楼主的数据
	 * @return
	 */
	public User selectUserByconversationChildId(Integer conversationChildId);
	
	/**
	 * 新增收藏方法
	 * @param userCollectionConversationChild
	 */
	void addConversationChildCollection(UserCollectionConversationChild userCollectionConversationChild);
	
	/**
	 * 删除收藏方法
	 * @param userCollectionConversationChild
	 * @return
	 */
	int deleteConversationChildCollection(UserCollectionConversationChild userCollectionConversationChild);
	
	/**
	 * 查询贴子收藏方法
	 * @param userCollectionConversationChild
	 * @return
	 */
	UserCollectionConversationChild selectConversationChildCollection(UserCollectionConversationChild userCollectionConversationChild);
	
	/**
	 * 根据用户id其收藏的帖子数据
	 * @param userId
	 * @return
	 */
	List<ConversationChild> selectCollectionConversationChildByUserId(Integer userId);
	
	/**
	 * 查询用户所发布的贴子 
	 * @param conversationChild
	 * @return
	 */
	List<ConversationChild> selectUserPublishConversationChild(ConversationChild conversationChild);
	
	/**
	 * 查询指定时间段内热门的贴子数据
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<ConversationChild> selectMaxConversationChildByDay(@Param("startTime") Date startTime,@Param("endTime") Date endTime);
	
	/**
	 * 获取贴子数据
	 * @param conversationChild
	 * @return
	 */
	ConversationChild selectConversationChild(ConversationChild conversationChild);
}

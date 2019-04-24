package com.mcgj.service;

import java.util.List;
import java.util.Map;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.ConversationChild;
import com.mcgj.entity.ConversationChildChild;
import com.mcgj.entity.UserCollectionConversationChild;

public interface IConversationChildService extends BaseService<ConversationChild, Integer>{
	
	/**
	 * 根据贴子id查询,获取当前帖子的第一层数据
	 * @return
	 */
	public Map<String,Object> selectConversationChildById(Integer id);
	
	/**
	 * 根据贴吧id查询帖子
	 * @return
	 */
	public Map<String,Object> selectConversationChildByConversationId(ConversationChild conversationChild);
	
	/**
	 * 新增帖子的方法
	 * @param conversationChild
	 */
	public void addConversationChild(ConversationChild conversationChild);
	
	/**
	 * 新增收藏方法
	 */
	void addConversationChildCollection(UserCollectionConversationChild userCollectionConversationChild);
	
	/**
	 * 删除收藏贴子方法
	 * @return
	 */
	void deleteConversationChildCollection(UserCollectionConversationChild userCollectionConversationChild);
	
	/**
	 * 根据用户Id和贴子名称查询收藏数据
	 * @param userCollectionConversationChild
	 * @return
	 */
	UserCollectionConversationChild selectConversationChildCollection(UserCollectionConversationChild userCollectionConversationChild);
	
	/**
	 * 查询用户所发布的贴子 
	 * @return
	 */
	Map<String,Object> selectUserPublishConversationChild(ConversationChild conversationChild);
	
	/**
	 * 查询对应天数内最为活跃的贴吧排名
	 * @param day 天数
	 * @return
	 */
	List<ConversationChild> selectMaxConversationChildByDay(Integer day);
	
	
	/**
	 * 添加楼层数据，爬虫专用
	 * @param conversationChild
	 */
	void addFloorDataSpider(ConversationChildChild conversationChildChild,Integer userId2);
	
}



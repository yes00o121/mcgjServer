package com.mcgj.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.ConversationChildChild;

//@Mapper
public interface ConversationChildChildMapper extends BaseService<ConversationChildChild,Integer>{
	
	/**
	 * 根据贴子id查询其下的楼层数据
	 * @return
	 */
	List<Map<String,Object>> selectCCCByCCId(ConversationChildChild conversationChildChild);
	
	
	/**
	 * 查询用户为查看的所有回复的楼层数据
	 * @param userId
	 * @return
	 */
	List<ConversationChildChild> selectConversationChildChildReplyByUserId(Integer userId);
	
	/**
	 * 将该用户所有的未查看楼层都修改为查看状态
	 * @param userId
	 */
	void updateConversationChildChildReplyByUerId(Integer userId);
	
	/**
	 * 查询楼层数据
	 * @param conversationChildChild
	 * @return
	 */
	ConversationChildChild selectConversationChildChild(ConversationChildChild conversationChildChild);
}

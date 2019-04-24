package com.mcgj.entity;

/**
 * 用户收藏贴子实体类
 * @author 杨晨
 *
 */
public class UserCollectionConversationChild extends BaseEntity{
	
	private Integer userId;//用户id
	
	private Integer conversationChildId;//收藏的贴子id

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getConversationChildId() {
		return conversationChildId;
	}

	public void setConversationChildId(Integer conversationChildId) {
		this.conversationChildId = conversationChildId;
	}
	
}

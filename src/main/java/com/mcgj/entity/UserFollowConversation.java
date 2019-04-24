package com.mcgj.entity;

/**
 * 用户关注的贴吧实体类
 * @author 杨晨
 */
public class UserFollowConversation extends BaseEntity{

	private Integer userId;//用户id
	
	private Integer conversationId;//帖子id
	
	private String conversationName;//用户关注的贴吧名称
	
	public String getConversationName() {
		return conversationName;
	}

	public void setConversationName(String conversationName) {
		this.conversationName = conversationName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getConversationId() {
		return conversationId;
	}

	public void setConversationId(Integer conversationId) {
		this.conversationId = conversationId;
	}
	
	
}

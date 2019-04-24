package com.mcgj.entity;
/**
 * 用户关注的贴吧实体类
 * @author 杨晨
 *
 */
public class UserConversation extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private Integer userId;//用户id
	
	private Integer conversationId;//关注的贴吧id
	
	private Integer grade;//贴吧等级

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

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	
}

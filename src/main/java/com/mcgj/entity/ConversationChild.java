package com.mcgj.entity;

import java.util.Date;

import com.mcgj.utils.CharUtil;
import com.mcgj.utils.StringUtil;

/**
 * 贴吧子帖子实体类
 * @author 杨晨
 *
 */
public class ConversationChild extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private Integer conversationId;//帖子对应的贴吧id

	private Integer userId;//帖子创建人id
	
	private String title;//帖子标题
	
	private String content;//帖子内容
	
	private Integer flow=0;//帖子流量,默认值为0
	
	private String createUserName;//创建帖子的用户名称
	
	private String lastUserName;//最后回复帖子的用户名称
	
	private Date lastDate;//帖子最后的回复时间
	
	private Integer replyNumber;//回帖数量
	
	private Integer conversationChildId;//对应的贴吧id
	
	private String photo;//用户头像
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Integer getConversationChildId() {
		return conversationChildId;
	}

	public void setConversationChildId(Integer conversationChildId) {
		this.conversationChildId = conversationChildId;
	}

	public Integer getReplyNumber() {
		return replyNumber;
	}

	public void setReplyNumber(Integer replyNumber) {
		this.replyNumber = replyNumber;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getLastUserName() {
		return lastUserName;
	}

	public void setLastUserName(String lastUserName) {
		this.lastUserName = lastUserName;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public Integer getConversationId() {
		return conversationId;
	}

	public void setConversationId(Integer conversationId) {
		this.conversationId = conversationId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return StringUtil.isNotEmpty(title) ? CharUtil.filterStr4Char(title, null) : null;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return StringUtil.isNotEmpty(content) ? CharUtil.filterStr4Char(content, null) : null;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getFlow() {
		return flow;
	}

	public void setFlow(Integer flow) {
		this.flow = flow;
	}
	
	
}

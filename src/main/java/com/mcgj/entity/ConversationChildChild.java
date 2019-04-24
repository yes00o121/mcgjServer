package com.mcgj.entity;

import com.mcgj.utils.CharUtil;
import com.mcgj.utils.StringUtil;

/**
 * 帖子楼层实体类
 * @author ad
 *
 */
public class ConversationChildChild extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private Integer conversationChildId;//楼层对应的帖子id
	
	private Integer userId;//楼层用户id
	
	private String content;//楼层回复内容
	
	private Integer isManage;//是否是楼主
	
	private Integer isLook = 0;//楼主是否查看过该回复,默认为没有
	
	private Boolean singleFloor;//只看楼主
	
	private String conversationName;//楼层对应的贴吧名称
	
	private String title;//楼层对应的贴子标题
	
	private String userName;//楼层对应的用户名称
	
	public String getConversationName() {
		return conversationName;
	}

	public void setConversationName(String conversationName) {
		this.conversationName = conversationName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getSingleFloor() {
		return singleFloor;
	}

	public void setSingleFloor(Boolean singleFloor) {
		this.singleFloor = singleFloor;
	}

	public Integer getIsLook() {
		return isLook;
	}

	public void setIsLook(Integer isLook) {
		this.isLook = isLook;
	}

	public Integer getIsManage() {
		return isManage;
	}

	public void setIsManage(Integer isManage) {
		this.isManage = isManage;
	}

	public Integer getConversationChildId() {
		return conversationChildId;
	}

	public void setConversationChildId(Integer conversationChildId) {
		this.conversationChildId = conversationChildId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getContent() {
		return StringUtil.isNotEmpty(content) ? CharUtil.filterStr4Char(content, null) : null;
	}

	public void setContent(String content) {
		this.content = content;
	}
}

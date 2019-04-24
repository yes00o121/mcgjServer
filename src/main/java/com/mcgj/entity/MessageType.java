package com.mcgj.entity;
/**
 * 消息类型枚举
 * @author ad
 *
 */
public enum MessageType {
	
	reply("回复"), notice("通知"), rivateChat("私聊");

	private String desc;
	
	private MessageType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String toString() {
		return this.desc;
	}

}

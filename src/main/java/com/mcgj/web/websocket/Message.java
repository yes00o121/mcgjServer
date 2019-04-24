package com.mcgj.web.websocket;

import com.mcgj.entity.MessageType;

/**
 * websocket消息对象
 * @author ad
 *
 */
public class Message {
	
	private Object message;//消息体
	
	private MessageType msgType;//消息类型,0楼层回复消息
	
	public Message(){
		
	}

	public Message(Object message, MessageType msgType) {
		super();
		this.message = message;
		this.msgType = msgType;
	}



	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public MessageType getMsgType() {
		return msgType;
	}

	public void setMsgType(MessageType msgType) {
		this.msgType = msgType;
	}
	
	
	
	
}

package com.mcgj.web.websocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * webSocket服务器
 * @author 杨晨
 *
 */
//@ServerEndpoint("/websocket")
@Component
@ServerEndpoint(value = "/websocket", encoders = { ServerEncoder.class })  
public class WebSocketServer {
	
	private static Logger  log = Logger.getLogger(WebSocketServer.class);

	private static int onlineCount = 0;// 连接数
	
	// concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
//	public static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
	
	//key为用户的token,根据token和在线用户进行通讯
	public static Map<String,WebSocketServer> webSocketMap = new ConcurrentHashMap<String,WebSocketServer>();
	
	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    
    /**
     * 连接成功调用
     */
    @OnOpen
    public void onOpen(Session session) {
        System.out.println(session.getQueryString());
        String token =session.getQueryString().split("=")[1];
    	this.session = session;
    	webSocketMap.put(token,this);
//    	sendMessage(token, new Message("消息推送测试。。。。。。。。。。。。。。。。。。。。",1));
        addOnlineCount();// 追加连接数量
    }
    
    /**
     * 接收用户发送的消息
     * 
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("收到客户端的消息。。。。。。。。。。。。。。" + message);

    }
    
    /**
     * 用户下线时调用
     */
    @OnClose
    public void onClose(Session session) {
        WebSocketServer.webSocketMap.remove(session.getQueryString().split("=")[1]);// 删除set中的socket
        removeOnlineCount();// 减少连接数量
//        System.out.println("断开连接。。。。。。。。。。。。。。。。");
    }
    
//    @OnError
//    public void onError(){
//    	System.out.println("连接出现错误。。。。。。。。。。。。。。。。");
//    }
    
    /**
     * 增加在线连接数量
     */
    private void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    /**
     * 减少在线连接数量
     */
    private void removeOnlineCount() {
        WebSocketServer.onlineCount--;
    }
    
    private static WebSocketServer getWebSocketConnection(String token){
    	return webSocketMap.get(token);
    }
    
    /**
     * 发送消息给指定用户
     */
    public static void sendMessage(String token,Message message){
    	try {
    		WebSocketServer wss = getWebSocketConnection(token);
    		if(wss != null){
    			wss.session.getBasicRemote().sendObject(message);
    		}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
    	
    }
    
}

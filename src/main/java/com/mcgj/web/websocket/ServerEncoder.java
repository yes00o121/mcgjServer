package com.mcgj.web.websocket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.alibaba.fastjson.JSONObject;

/**
 * websocket编码
 * @author ad
 *
 */
public class ServerEncoder implements Encoder.Text<Message>{

	@Override
	public void init(EndpointConfig config) {
		
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public String encode(Message object) throws EncodeException {
		try {  
            return JSONObject.toJSONString(object);  
        } catch (Exception e) {
            e.printStackTrace();  
            return null;  
        }  
	}

}

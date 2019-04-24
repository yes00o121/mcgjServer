package com.mcgj.web.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 一定要注入这个类,否则springboot不能用，不要问为什么
 * springboot非常坑,所有都是内置的，导致很多都要注入	，坑坑坑
 * @dscript 注入ServerEndpointExporter，这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint。 要注意，如果使用独立的servlet容器，而不是直接使用springboot的内置容器，就不要注入ServerEndpointExporter， 因为它将由容器自己提供和管理。
 * @author 杨晨
 *
 */
@Configuration
public class WebSokcetConfig {
	
	@Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}

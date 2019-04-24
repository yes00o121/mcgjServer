package com.mcgj.init;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations={"classpath:spring/applicationContext.xml"})//引入配置文件
public class Config {

}

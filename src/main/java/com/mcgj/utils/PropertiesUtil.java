package com.mcgj.utils;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 读取配置文件工具类
 * @author ad
 *
 */
public class PropertiesUtil {
	
	private static Logger log =Logger.getLogger(PropertiesUtil.class);
	
	private static Properties properties = new Properties();
	
	/**
	 * 获取配置中的value
	 * @param 文件路径
	 * @param key
	 * @return
	 */
	public static String get(String dirName,String key){
		try{
			InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream(dirName);
			properties.load(is);//加载文件流
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return (String)properties.get(key);
	}
	
	/**
	 * 获取登录会话时长
	 * @return
	 */
	public static Integer getLoginDelay(){
		return Integer.parseInt(PropertiesUtil.get("delay.properties","login_delay"));
	}
	
	/**
	 * 获取验证码会话时长
	 * @return
	 */
	public static Integer getVerificationDelay(){
		return Integer.parseInt(PropertiesUtil.get("delay.properties","verification_delay"));
	}
}

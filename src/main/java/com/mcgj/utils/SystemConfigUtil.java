package com.mcgj.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mcgj.dao.SystemConfigMapper;
import com.mcgj.entity.SystemConfig;

/**
 * 系统配置工具类,获取系统配置的数据
 * @author 杨晨
 * @date 2018-12-12 14:47
 * @address 昆明
 */
public class SystemConfigUtil {
	
	private SystemConfigUtil(){}
	
	//存放配置数据
	private static Map<String,String> values = new HashMap<String,String>();
	
	static{
		reload();
	}
	
	/**
	 * 根据Key获取value
	 * @return value
	 */
	public static String getSystemConfigByKey(String key){
		return values.get(key);
	}
		
	//刷新配置数据
	public  static void reload(){
		SystemConfigMapper systemConfigDAO = (SystemConfigMapper)SpringUtil.getBean(SystemConfigMapper.class);
		List<SystemConfig> selectAll = systemConfigDAO.selectAll();
		for(SystemConfig config:selectAll){
			values.put(config.getKey(), config.getValue());
		}
	}
	
}

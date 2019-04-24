package com.mcgj.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.mcgj.dao.FileRepertoryMapper;
import com.mcgj.entity.FileRepertory;
import com.mcgj.redis.RedisHashUtil;
import com.mcgj.utils.PropertiesUtil;
import com.mcgj.utils.SpringUtil;

/**
 * 文件仓库bean
 * @author 杨晨
 * @date 2019-03-01
 * @address 昆明
 * @descript 初始化文件仓库bean,提供一些初始化方法
 */
public class FileRepertoryBean {
	
	Logger log = Logger.getLogger(FileRepertoryBean.class);
	
	@Autowired
	private FileRepertoryMapper fileRepertoryMapper;
	
	
	public void init(){
		List<FileRepertory> list = fileRepertoryMapper.selectAll();
		if(list != null && list.size() != 0){
			Map<String,Object> tempMap = new HashMap<>();
			for(FileRepertory file:list){
				tempMap.put(file.getAddress(), file.getMongodbId());
			}
			RedisHashUtil.putAll(PropertiesUtil.get("redisConifg.properties","fileRepertory" ), tempMap);
			System.out.println("初始化文件仓库到redis...");
		}
	}
	
	public void close(){
		
	}
}

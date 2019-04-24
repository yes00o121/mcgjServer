package com.mcgj.service;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.SystemConfig;

/**
 * 系统配置接口
 * @author 杨晨
 * @create_date 2018-12-05 12:30
 * @address 合肥
 *
 */
public interface ISystemConfigService extends BaseService<SystemConfig, Integer>{

	/**
	 * 根据key获取配置的值
	 * @param key
	 * @return
	 */
	String selectSystemConfigByKey(String key);
}

package com.mcgj.dao;

import java.util.List;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.SystemConfig;

/**
 * 系统配置dao映射接口
 * @author 杨晨
 * @create_date 2018-12-05 12:33
 * @address 合肥
 *
 */
public interface SystemConfigMapper extends BaseService<SystemConfig, Integer>{
	
	/**
	 * 查询所有数据
	 * @return
	 */
	List<SystemConfig> selectAll();
}

package com.mcgj.service;

import java.util.List;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.Dict;

public interface IDictService extends BaseService<Dict,Integer>{
	
	/**
	 * 根据传入的代码值查询
	 * @return
	 */
	public List<Dict> selectDictByCode(String codeValue);
}

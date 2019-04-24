package com.mcgj.dao;

import java.util.List;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.Dict;

public interface DictMapper extends BaseService<Dict,Integer>{
	
	/**
	 * 根据传入的代码值查询
	 * @return
	 */
	public List<Dict> selectDictByCode(String codeValue);
}

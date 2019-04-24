package com.mcgj.dao;

import java.util.List;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.FileRepertory;

/**
 * 文件仓库dao
 * @author 杨晨
 * @date 2019-03-01
 */
public interface FileRepertoryMapper extends BaseService<FileRepertory, Integer>{
	
	/**
	 * 查询所有的文件仓库数据
	 * @return
	 */
	public List<FileRepertory> selectAll();
}

package com.mcgj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcgj.dao.OperationMapper;
import com.mcgj.entity.Operation;

/**
 * 操作业务层实现类
 * @author 杨晨
 * @date 2019-03-28
 * @address 海口
 */
@Service
public class OperationServiceImpl implements IOperationService{
	
	@Autowired
	private OperationMapper operationMapper;

	@Override
	public void delete(Integer id) {
		
	}

	@Override
	public void insert(Operation record) {
		operationMapper.insert(record);
	}

	@Override
	public void update(Operation record) {
		
	}

	@Override
	public Operation selectById(Integer id) {
		return null;
	}

}

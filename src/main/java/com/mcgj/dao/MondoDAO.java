package com.mcgj.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

/**
 * mondoDAO
 * @author 杨晨
 *
 */
@Service
public class MondoDAO {
	public MondoDAO(){
		System.out.println("初始化mongodao...");
	}
	@Autowired
	private MongoOperations mongoTemplate;
	public MongoOperations getMongoTemplate(){
		return mongoTemplate;
	}
}

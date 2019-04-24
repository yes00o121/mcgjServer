package com.mcgj.redis;

import org.springframework.dao.support.PersistenceExceptionTranslator;

import redis.clients.jedis.Jedis;

public interface RedisConnectionFactory extends PersistenceExceptionTranslator{
	
	public Jedis getConnect();

	public void returnResource(Jedis jedis);
}

package com.mcgj.redis;

import com.mcgj.utils.SpringUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisCacheBase2 {
	

    public static final String STRING_NULL = "nil";

    private static JedisPool pool = null;

    private static RedisConnectionFactory factory;

    private static RedisConnectionFactory getFactory() {
        if (factory == null) {
            factory = (JedisCacheBase) SpringUtil
                    .getBean(JedisCacheBase.class);
        }
        return factory;
    }

    private static JedisPool getPool() {
        if (pool == null) {
            pool = new JedisPool(new JedisPoolConfig(), "localhost");
        }
        return pool;
    }

    protected static Jedis getConn() {
        return getFactory().getConnect();
    }

    protected static void returnConn(Jedis jedis) {
        getFactory().returnResource(jedis);
    }
    
    protected static void closeConn(Jedis jedis) {
    	if(jedis!= null)
    		jedis.close();
    }
}

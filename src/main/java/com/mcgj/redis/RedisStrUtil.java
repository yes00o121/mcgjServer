package com.mcgj.redis;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.mcgj.utils.MessageUtil;

import redis.clients.jedis.Jedis;

/**
 * redis 字符串工具类
 * @author 杨晨
 *
 */
public class RedisStrUtil extends JedisCacheBase2{
	
	private static Logger log = Logger.getLogger(RedisStrUtil.class);
	
	/**
     * 设置key值
     * @param key
     * @return
     */
    public static void set(String key,String value) {
    	ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        Jedis jedis = getConn();
        try {
        	value = jedis.set(key, value);
        } catch (Exception e) {
            log.error(MessageUtil.MSG_OPERATION_CACHE_ERROR, e);
        } finally {
        	closeConn(jedis);
        }
    }
}

package com.mcgj.redis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mcgj.utils.MessageUtil;

import redis.clients.jedis.Jedis;

/**
 * redis hash工具类
 * @author ad
 *
 */
@Service
public class RedisHashUtil extends JedisCacheBase2{

	 private static Log log = LogFactory.getLog(RedisHashUtil.class);
	 
	    public static Long put(String key, String id, Object value) {
	        if (id == null) {
	            return Long.valueOf(0L);
	        }
	        String str_value = JSON.toJSONString(value,
	                new SerializerFeature[] { SerializerFeature.WriteClassName });
	        Long l = null;
	        Jedis jedis = getConn();
	        try {
	            l = jedis.hset(key, id, str_value);
	        } catch (Exception e) {
	        	log.error(MessageUtil.MSG_OPERATION_CACHE_ERROR, e);
	        } finally {
//	            returnConn(jedis);
	        	closeConn(jedis);
	        }

	        return l;
	    }
	    
	    /**
	     * 添加list数据
	     * @param key
	     * @param value
	     * @return
	     */
	    public static Long put(String key, String value) {
	        if (value == null) {
	            return Long.valueOf(0L);
	        }
	        Long l = null;
	        Jedis jedis = getConn();
	        try {
	            l = jedis.lpush(key, value);
	        } catch (Exception e) {
	        	log.error(MessageUtil.MSG_OPERATION_CACHE_ERROR, e);
	        } finally {
	        	closeConn(jedis);
	        }

	        return l;
	    }
	    
	    /**
	     * 获取list数据
	     * @param key
	     * @param value
	     * @return
	     */
	    public static List<String> getListAll(String key) {
	        if (key == null) {
	            return null;
	        }
	        List<String> l = null;
	        Jedis jedis = getConn();
	        try {
	            l = jedis.lrange(key, 0, -1);
	        } catch (Exception e) {
	        	log.error(MessageUtil.MSG_OPERATION_CACHE_ERROR, e);
	        } finally {
	        	closeConn(jedis);
	        }

	        return l;
	    }
	    
	    /**
	     * 判断某个值是否存在list中
	     * @param key
	     * @param value
	     * @return
	     */
	    public static boolean isExistList(String key,String value){
	    	if(key == null){
	    		return false;
	    	}
	    	try {
	    		List<String> list = getListAll(key);
	    		if(list.contains(value)){
	    			return true;
	    		}
			} catch (Exception e) {
				log.error(MessageUtil.MSG_OPERATION_CACHE_ERROR);
			}
	    	return false;
	    }
	    
	    /**
	     * 写入过期数据
	     * @param key
	     * @param id
	     * @param value
	     * @param time
	     * @return
	     */
	    public static void setex(String key, Object value,Integer time) {
	    	String str_value = JSON.toJSONString(value,
	                new SerializerFeature[] { SerializerFeature.WriteClassName });
	        Jedis jedis = getConn();
	        try {
//	            l = jedis.hset(key, id, str_value);
	            jedis.setex(key, time,str_value);
	        } catch (Exception e) {
	            log.error(MessageUtil.MSG_OPERATION_CACHE_ERROR, e);
	        } finally {
//	            returnConn(jedis);
	        	closeConn(jedis);
	        }
	    }
	    /**
	     * 根据Key获取值
	     * @param key
	     * @return
	     */
	    public static Object get(String key) {
	    	ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
	    	String value = null;
	        Jedis jedis = getConn();
	        try {
	        	value = jedis.get(key);
	        } catch (Exception e) {
	            log.error(MessageUtil.MSG_OPERATION_CACHE_ERROR, e);
	        } finally {
//	            returnConn(jedis);
	        	closeConn(jedis);
	        }
	        Object result = null;
	        if(value != null && value.length() >0){
	        	result = JSON.parse(value);
	        }
	        return result;
	    }
	    
	    public static <T> void putAll(String key, Map<String, T> hash) {
	        if ((hash == null) || (hash.isEmpty())) {
	            return;
	        }
	        Map<String, String> hashString = new HashMap<String, String>();
	        Iterator<String> iterator = hash.keySet().iterator();
	        while (iterator.hasNext()) {
	            String keyValue = (String) iterator.next();
	            hashString.put(keyValue, //key
	                    JSON.toJSONString(hash.get(keyValue),
	                            new SerializerFeature[] { SerializerFeature.WriteClassName }) //value
	                    );
	        }
	        Jedis jedis = getConn();
	        try {
	            jedis.hmset(key, hashString);
	        } catch (Exception e) {
	        	log.error(MessageUtil.MSG_OPERATION_CACHE_ERROR, e);
	        } finally {
//	            returnConn(jedis);
	        	closeConn(jedis);
	        }
	    }

	    public static Boolean exists(String key, Long id) {
	        if (id == null) {
	            return Boolean.valueOf(false);
	        }
	        Boolean exist = Boolean.valueOf(false);
	        Jedis jedis = getConn();
	        try {
	            exist = jedis.hexists(key, id.toString());
	        } catch (Exception e) {
	        	log.error(MessageUtil.MSG_OPERATION_CACHE_ERROR, e);
	        } finally {
//	            returnConn(jedis);
	        	closeConn(jedis);
	        }
	        return exist;
	    }

	    public static Object get(String key, String id) {
	        String bulk = null;
	        Jedis jedis = getConn();
	        try {
	            bulk = jedis.hget(key, id);
	        } catch (Exception e) {
	        	log.error(MessageUtil.MSG_OPERATION_CACHE_ERROR, e);
	        } finally {
//	            returnConn(jedis);
	        	closeConn(jedis);
	        }
	        Object value = null;
	        if ((bulk != null) && (bulk.length() > 0)) {
	            value = JSON.parse(bulk);
	        }

	        return value;
	    }

	    @SuppressWarnings("unchecked")
	    public static <T> Map<String, T> getMutiple(String key, Set<String> ids) {
	        if ((ids == null) || (ids.isEmpty())) {
	            return new HashMap<String, T>(0);
	        }
	        String[] id_array = (String[]) ids.toArray(new String[0]);

	        Jedis jedis = getConn();
	        Map<String, T> objMap = new HashMap<String, T>();
	        try {
	            List<String> valueList = jedis.hmget(key, id_array);
	            int i = 0;
	            for (String value : valueList) {
	                if (value != null) {
	                    objMap.put(id_array[i], (T) JSON.parse(value));
	                }
	                i++;
	            }
	        } catch (Exception e) {
	        	log.error(MessageUtil.MSG_OPERATION_CACHE_ERROR, e);
	        } finally {
//	            returnConn(jedis);
	        	closeConn(jedis);
	        }

	        return objMap;
	    }

	    @SuppressWarnings("unchecked")
	    public static <V> Map<String, V> getAll(String key) {
	        Jedis jedis = getConn();
	        Map<String, V> hashObject = new HashMap<String, V>();
	        try {
	            Map<String, String> value = jedis.hgetAll(key);
	            Iterator<String> iterator = value.keySet().iterator();
	            while (iterator.hasNext()) {
	                String keyValue = (String) iterator.next();
	                hashObject.put(keyValue, (V) JSON.parse((String) value.get(keyValue)));
	            }
	        } catch (Exception e) {
	        	log.error(MessageUtil.MSG_OPERATION_CACHE_ERROR, e);
	        } finally {
//	            returnConn(jedis);
	        	closeConn(jedis);
	        }

	        return hashObject;
	    }

	    public static void remove(String key, Long... ids) {
	        Jedis jedis = getConn();
	        try {
	            for (Long id : ids) {
	                if (id != null) {
	                    jedis.hdel(key, new String[] { id.toString() });
	                }
	            }
	        } catch (Exception e) {
	        	log.error(MessageUtil.MSG_OPERATION_CACHE_ERROR, e);
	        } finally {
//	            returnConn(jedis);
	        	closeConn(jedis);
	        }
	    }

	    public static void remove(String key, String... ids) {
	        Jedis jedis = getConn();
	        try {
	            for (String id : ids)
	                if (id != null) jedis.hdel(key, new String[] { id });
	        } catch (Exception e) {
	        	log.error(MessageUtil.MSG_OPERATION_CACHE_ERROR, e);
	        } finally {
//	            returnConn(jedis);
	        	closeConn(jedis);
	        }
	    }

	    public synchronized static void deleteKey(String... keys) {
	        Jedis jedis = getConn();
	        try {
	            jedis.del(keys);
	        } catch (Exception e) {
	        	log.error(MessageUtil.MSG_OPERATION_CACHE_ERROR, e);
	        } finally {
//	            returnConn(jedis);
	        	closeConn(jedis);
	        }
	    }

	    public static long size(String key) {
	        Long l = null;
	        Jedis jedis = getConn();
	        try {
	            l = jedis.hlen(key);
	        } catch (Exception e) {
	        	log.error(MessageUtil.MSG_OPERATION_CACHE_ERROR, e);
	        } finally {
//	            returnConn(jedis);
	        	closeConn(jedis);
	        }

	        return l.longValue();
	    }
}
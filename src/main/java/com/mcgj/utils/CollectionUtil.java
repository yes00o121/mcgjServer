package com.mcgj.utils;

import java.util.Collection;

/**
 * 集合工具
 * @author 杨晨
 * @date 2019-03-21
 * @address 海口
 *
 */
public class CollectionUtil {

	public static boolean isEmpty(Collection<Object> coll){
		if(coll == null || coll.size() == 0){
			return true;
		}
		return false;
	}
	
	public static boolean isNotEmpty(Collection<Object> coll){
		if(coll != null && coll.size() > 0){
			return true;
		}
		return false;
	}
}

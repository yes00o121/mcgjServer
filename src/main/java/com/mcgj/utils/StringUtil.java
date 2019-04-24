package com.mcgj.utils;

/**
 * 字符串工具类
 * @author 杨晨
 *@create_date 2018-12-05 21:47
 *@address 合肥
 */
public class StringUtil {
	
	/**
	 * 是否不为空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		if(str != null && !"".equals(str)){
			return true;
		}
		return false;
	}
	
	public static boolean isEmpty(String str){
		if(str == null || "".equals(str)){
			return true;
		}
		return false;
	}
	
}

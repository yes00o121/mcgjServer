package com.mcgj.utils;

import java.util.ArrayList;
import java.util.List;
/**
 * 分页工具类
 * @author 杨晨
 *
 */
public class PageUtil {
	public static <T> List<T> createPage(int start, int limit, List<T> list) {
		int startPage = (start-1)*limit+1;//开始页
		int endPage = start*limit;//结束页
		List<T> lists = new ArrayList<T>();
		for(int i=startPage-1;i<=endPage-1;i++){
			if(i < list.size() && list.get(i) != null){
				lists.add(list.get(i));
			}
		}
		return lists;
	}
}

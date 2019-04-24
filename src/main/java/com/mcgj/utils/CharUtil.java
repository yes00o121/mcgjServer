package com.mcgj.utils;

import java.util.Map;

/**
 * 字符处理工具
 * @author 杨晨
 * @address 深圳
 * @date 2019-02-25
 *
 */
public class CharUtil {
	
	//过滤特殊字符
	private static String[][] FilterChars={{"<","&lt;"},{">","&gt;"},{" ","&nbsp;"},{"\"","&quot;"},{"&","&amp;"},   
            {"/","&#47;"},{"\\","&#92;"},{"\n","<br>"}};   
	
	//过滤通过javascript脚本处理并提交的字符   
	private static String[][] FilterScriptChars={{"\n","\'+\'\\n\'+\'"},   
	                                {"\r"," "},{"\\","\'+\'\\\\\'+\'"},   
	                                        {"\'","\'+\'\\\'\'+\'"}};   
	
	public static String urlFilter(String url){
		for(String[] str:FilterChars){
			if(url.contains(str[0])){
				url = url.replaceAll(str[0], str[1]);
			}
		}
		return url;
	}
	
	/**
	 * 替换str中的四个字节字符
	 * @param str 字符串
	 * @param replace 需要替换的内容
	 * @return
	 */
	public static String filterStr4Char(String str,String replace){
		if(StringUtil.isEmpty(str)){
			return null;
		}
		if(StringUtil.isEmpty(replace)){
			return str.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
		}
		return str.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", replace);
	}
	
	/**
	 * 替换str中的四个字节字符
	 * @param str 字符串
	 * @param replace 需要替换的内容
	 * @return
	 */
	public static Map<String,Object> filterStr4Char(Map<String, Object> map,String replace){
		if(MapUtil.isEmpty(map)){
			return null;
		}
		for(String str:map.keySet()){
			if(map.get(str) == null){
				continue;
			}
			Object obj = map.get(str);
			//如果是字符串数组
			if(obj instanceof String[]){
				String[] strArr = (String[])obj;
				String[] newArr = new String[strArr.length];
				int i;
				for(i = 0;i < strArr.length;i++){
					if(StringUtil.isNotEmpty(strArr[i])){
						newArr[i] = CharUtil.filterStr4Char(strArr[i], replace);
					}
				}
				map.put(str, newArr);
				continue;
			}
			//如果是字符串
			if(obj instanceof String){
				map.put(str, CharUtil.filterStr4Char(obj.toString(), replace));
				continue;
			}
			throw new RuntimeException("无法解析Object中的对象,找不到"+obj.getClass().getName() + "请确认是否有进行该类型的解析操作。");
		}
		return map;
	}
	
	
}

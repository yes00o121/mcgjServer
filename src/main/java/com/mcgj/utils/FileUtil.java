package com.mcgj.utils;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.mcgj.redis.RedisHashUtil;
import com.mcgj.service.IRemoteFileMongoSer;
import com.mcgj.service.MongoDBRemoteFileService;

/**
 * 文件工具
 * @author 杨晨
 * @date 2019-04-25
 * @address 深圳
 */
public class FileUtil {
	
	private static IRemoteFileMongoSer mongoDBRemoteFileService;
	
	static{
		mongoDBRemoteFileService = (MongoDBRemoteFileService)SpringUtil.getBean(IRemoteFileMongoSer.class);
	}
	
	private static Logger log = Logger.getLogger(FileUtil.class);
	
	/**
	 * 从文件仓库中获取请求的文件id,没有返回null
	 * @param url
	 * @return
	 */
	public static String getFileRepertoryData(String url){
		if(StringUtil.isEmpty(url)){
			throw new RuntimeException(MessageUtil.MSG_REQUEST_URL_IS_NULL);
		}
		Object value = RedisHashUtil.get(ConstantUtil.FILE_REPERTORY_KEY, url);
		if(value == null){
			return null;
		}
		return value.toString();
	}
}

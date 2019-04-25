package com.mcgj.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcgj.dao.FileRepertoryMapper;
import com.mcgj.entity.FileRepertory;
import com.mcgj.redis.RedisHashUtil;
import com.mcgj.utils.ConstantUtil;
import com.mcgj.utils.HttpClientUtil;
import com.mcgj.utils.MessageUtil;

/**
 * 文件业务层
 * @author 杨晨
 * @date 2019-04-25
 * @address 深圳
 */
@Service
public class FileServiceImpl  implements IFileService{
	
	private static Logger log = Logger.getLogger(FileServiceImpl.class);
	
	@Autowired
	protected IRemoteFileMongoSer mongoDBRemoteFileService;
	
	@Autowired
	private FileRepertoryMapper fileRepertoryMapper;

	@Override
	public synchronized String upLoadRemoteFile(String url) {
		if(!HttpClientUtil.isHttp(url)){
			url = HttpClientUtil.perfectHttp(url);
		}
		Object mongoId = RedisHashUtil.get(ConstantUtil.FILE_REPERTORY_KEY, url);
		//判断缓存中是否有该图片,没有插入数据库和缓存
		if(mongoId != null){
			return mongoId.toString();
		}
		InputStream fileInputStream = null;
		try {
			//获取请求的文件的数据流
			fileInputStream = HttpClientUtil.getFileInputStream(url);
			String mondoid = mongoDBRemoteFileService.upload(fileInputStream);
			FileRepertory record = new FileRepertory(HttpClientUtil.getHost(url),url,mondoid);
			//将数据插入
			fileRepertoryMapper.insert(record);
			RedisHashUtil.put(ConstantUtil.FILE_REPERTORY_KEY, url, mondoid);
			return mondoid;
		} catch (Exception e) {
			log.error(MessageUtil.MSG_UPLOAD_FILE_ERROR + ":"+url);
			return null;
		}finally {
			if(fileInputStream != null){
				try {
					fileInputStream.close();
				} catch (IOException e) {
					log.error(MessageUtil.MSG_CLOSE_STREAM_ERROR);
				}
			}
		}
	}

}

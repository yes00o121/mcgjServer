package com.mcgj.web.controller;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mcgj.dao.FileRepertoryMapper;
import com.mcgj.entity.FileRepertory;
import com.mcgj.redis.RedisHashUtil;
import com.mcgj.utils.HttpClientUtil;
import com.mcgj.utils.MessageUtil;
import com.mcgj.utils.PropertiesUtil;

/**
 * 文件控制器
 * @author 杨晨
 * @date 2019-02-28
 * @descript 提供一些文件相关的方法
 *
 */
@Controller
@RequestMapping("/file")
public class FileController extends AbstractBaseController{
	
	private Logger log = Logger.getLogger(FileController.class);
	
	@Autowired
	private FileRepertoryMapper fileRepertoryMapper;
	
	/**
	 * 上传其他网站的文件
	 * @param url 地址
	 * @return
	 */
	@RequestMapping("/upLoadRemoteFile")
	@ResponseBody
	public synchronized String  upLoadRemoteFile(@RequestParam("url") String url){
		
		Object mongoId = RedisHashUtil.get(PropertiesUtil.get("redisConifg.properties","fileRepertory" ), url);
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
			RedisHashUtil.put(PropertiesUtil.get("redisConifg.properties", "fileRepertory"), url, mondoid);
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

package com.mcgj.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.mcgj.service.IRemoteFileMongoSer;
/**
 * 所有控制器父类，实现了一些Mongo的操作
 * @author ad
 *
 */
public class AbstractBaseController {
	
	@Autowired
	protected IRemoteFileMongoSer mongoDBRemoteFileService;
	
	/**
	 * 上传文件
	 * @param file
	 * @param entity
	 * @param propertyName
	 * @throws Exception
	 */
	public String uploadFile(MultipartFile file) throws Exception{
		return "/common/image?imgId="+mongoDBRemoteFileService.upload(file.getInputStream());
	}
}

package com.mcgj.web.controller;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mcgj.dao.FileRepertoryMapper;
import com.mcgj.entity.FileRepertory;
import com.mcgj.redis.RedisHashUtil;
import com.mcgj.service.IFileService;
import com.mcgj.utils.ConstantUtil;
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
	private IFileService fileService;
	
	/**
	 * 上传其他网站的文件
	 * @param url 地址
	 * @return
	 */
	@RequestMapping(value = "/upLoadRemoteFile" )
	@ResponseBody
	public String  upLoadRemoteFile(@RequestParam("url") String url){
		return fileService.upLoadRemoteFile(url);
	}
}

package com.mcgj.web.common;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mcgj.redis.RedisHashUtil;
import com.mcgj.utils.PropertiesUtil;
import com.mcgj.utils.RandomGraphic;
import com.mcgj.web.controller.AbstractBaseController;

@RequestMapping("/common")
@Controller
public class CommonController extends AbstractBaseController{
	
	private static final Logger log = LoggerFactory.getLogger(CommonController.class);
	
	
	/**
	 * 返回图片方法
	 * @param imgId
	 * @param response
	 */
	@RequestMapping("/image")
	@Async
	public void image(String imgId,HttpServletResponse response) {
		response.setContentType("image");
		try {
			ServletOutputStream out = response.getOutputStream();
			mongoDBRemoteFileService.download(imgId, out);
		} catch (Exception e) {
			log.error(Marker.ANY_MARKER, e);
		}
	}
	/**
	 * 下载文件
	 * @param mongoid
	 * @param fileName
	 * @param model
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/downloadFile")
	public void downloadFile(String mongoid,String fileName,Model model,HttpServletResponse response) throws IOException {
		response.setContentType("application/x-msdownload;");
		response.setHeader("Content-disposition", "attachment; filename="+(fileName!=null?fileName:mongoid));
		mongoDBRemoteFileService.download(mongoid, response.getOutputStream());
	}
	/**
	 * 上传文件方法
	 * @return
	 */
	@RequestMapping("/uploadFile")
	@ResponseBody
	public String uploadFile(MultipartFile file){
		String imageUrl = "";
		try{
			imageUrl = super.uploadFile(file);
		}catch(Exception e){
			e.printStackTrace();
		}
		return imageUrl;
	}
	
	/**
	 * 生成验证码
	 */
	@RequestMapping("/generateVerification")
	@Async
	public void generateVerification(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		try{
			//获取前端本地存放的verificationCode
			String verificationCode =request.getParameter("verificationCode");
			if(verificationCode == null || "".equals(verificationCode)){
				throw new RuntimeException("获取验证码失败");
			}
//			Properties p = new Properties();
//			FileInputStream file = new FileInputStream("./delay.properties");
//			InputStream  is = CommonController.class.getClassLoader().getResourceAsStream("delay.properties");
//			p.load(is);
//			String time = p.getProperty("verification_delay");//获取超时时长
			Integer time = PropertiesUtil.getVerificationDelay();
			RandomGraphic rg = new RandomGraphic();
			String strs = rg.getRandomCharacter();//获取随机生成的验证字符
			BufferedImage bi = rg.createGraphic(strs);//生成图片
			RedisHashUtil.setex(verificationCode,strs,time);//将当前浏览器生成的唯一标识作为key存入，方便后期验证
			//返回图片给用户
			ImageIO.write(bi,"jpg",response.getOutputStream());
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
	}
	
	/**
	 * 上传网络图片
	 * @param url 图片地址
	 * @return 返回图片主键
	 */
	@RequestMapping("/upNetWorkImg")
	@Async
	@ResponseBody
	public String upNetWorkImg(String url,HttpServletRequest request){
		HttpURLConnection conn = null;
		String imgId = "";//图片主键
		try {
			URL requestURL = new URL(url);
			conn = (HttpURLConnection)requestURL.openConnection();
			if(conn.getResponseCode() == 200){
				//获取正常,获取图片流
				InputStream inputStream = conn.getInputStream();
				log.info("图片上传成功:"+imgId);
				//上传图片
				imgId = mongoDBRemoteFileService.upload(inputStream);
				inputStream.close();//关闭输入流
			}else{
				throw new RuntimeException("上传图片异常,请求网络图片异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imgId;
	}
}

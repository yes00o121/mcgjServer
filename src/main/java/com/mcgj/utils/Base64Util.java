package com.mcgj.utils;

import java.io.FileInputStream;
import java.io.RandomAccessFile;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 * base64编码器
 * @author 杨晨
 *
 */
public class Base64Util {
	
	Logger log = Logger.getLogger(Base64Util.class);
	
	/**
	 * 字符串BASE64 编码
	 * @return
	 */
	public static String getStrBASE64(String str){
		return new sun.misc.BASE64Encoder().encode(str.getBytes()); // 具体的编码方法  
	}
	
	/** 
     * 图片BASE64 编码 
     *  
     * @author 
     */ 
	public static String getPicBASE64(String picPath) {  
        String content = null;  
        try {  
            FileInputStream fileForInput = new FileInputStream(picPath);  
            byte[] bytes = new byte[fileForInput.available()];  
            fileForInput.read(bytes);  
            content = new sun.misc.BASE64Encoder().encode(bytes); // 具体的编码方法  
            fileForInput.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return content;  
    }  
	
	/** 
     * 对图片BASE64 解码 
     *  
     * @author 
     */
	public static void getPicFormatBASE64(String str, String picPath) {  
        try {  
            byte[] result = new sun.misc.BASE64Decoder().decodeBuffer(str  
                    .trim());  
            RandomAccessFile inOut = new RandomAccessFile(picPath, "rw"); // r,rw,rws,rwd  
            inOut.write(result);  
            inOut.close();  
        } catch (Exception e) {
            e.printStackTrace();  
        }  
    }  
	
	/**
	 * 解密
	 * @param param
	 */
	public static String dcode(String param){
		if(param == null)
			throw new RuntimeException(MessageUtil.MSG_SECURITY_DCODE_ERROR);
		return new String(new Base64().decodeBase64(param));
	}
	
	/**
	 * 加密
	 * @param param
	 */
	public static String ecode(String param){
		if(param == null)
			throw new RuntimeException(MessageUtil.MSG_SECURITY_ECODE_ERROR);
		return new String(new Base64().encodeToString(param.getBytes()));
	}
	
}

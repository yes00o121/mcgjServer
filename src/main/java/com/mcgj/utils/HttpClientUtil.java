package com.mcgj.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;


public class HttpClientUtil {
	
	private static Logger log = Logger.getLogger(HttpClientUtil.class);
    
    /***
     * 使用get请求获取数据
     * @param url 请求服务器地址
     * @param param 请求参数
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        	log.error(e);
//            LoggerUtil.error(HttpClientUtil.class, e.getMessage(), e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
            	log.error(e2);
//                LoggerUtil.error(HttpClientUtil.class, e2.getMessage(), e2);
            }
        }
        return result;
    }
    
    /***
     * 使用post请求获取数据
     * @param url 请求服务器地址
     * @param param 请求参数
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        	log.error(e);
//            LoggerUtil.error(HttpClientUtil.class, e.getMessage(), e);
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }catch(IOException ex){
            	log.error(ex);
//                LoggerUtil.error(HttpClientUtil.class, ex.getMessage(), ex);
            }
        }
        return result;
    }
    
    /**
     * 获取远程文件流
     * @param url
     * @return
     */
    public static InputStream getFileInputStream(String url){
    	HttpURLConnection conn = null;
    	try {
    		url = url.startsWith("//") ? url.replaceAll("//", "http://") : url;
    		url = url.startsWith("http://") ? url : url.replaceAll(url, "http://" + url);
    		URL realUrl = new URL(url);
    		conn = (HttpURLConnection) realUrl.openConnection();
        	if(conn.getResponseCode() == 200){
        		return conn.getInputStream();
        	}else{
        		log.error(MessageUtil.MSG_GET_FILEINPUTSTREAM_ERROR+ ":"+conn.getResponseCode());
        		throw new RuntimeException(MessageUtil.MSG_GET_FILEINPUTSTREAM_ERROR);
        	}
		} catch (Exception e) {
			log.error(e);
		}finally {
			conn.disconnect();
		}
    	return null;
    }
    
    /**
     * 获取网站的host
     * @param url
     * @return
     */
    public static String getHost(String url){
    	try {
			URL ul = new URL(url);
			return ul.getHost();
		} catch (Exception e) {
			log.error(MessageUtil.MSG_UNKONW_ERROR);
		}
    	return null;
    }
    
    public static void main(String[] args) {
		String url = "http://tb1.bdstatic.com/tb/cms/frs/bg/default_head20141014.jpg";
		try {
			URL ul = new URL(url);
			System.out.println(ul.getHost());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		System.out.println();
//		getFileInputStream(url);
	}
}

package com.mcgj.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

/**
 * 随机绘制图形
 * @author ad
 *
 */
public class RandomGraphic {
	
	private int workHeight =10;//字符的高度
	
	private int workWidth = 10;//字符宽度
	
	private int fontSize = 15;//字符大小
	
	private int digit =4;//验证码个数
	
	private String[] strs={"1","2","3","4","5","6","7","8","9","0","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
	
	/**
	 * 根据传入参数绘制图片
	 * @param chars
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public BufferedImage createGraphic(String chars) throws FileNotFoundException, IOException{
		BufferedImage img = new BufferedImage(80,40, BufferedImage.TYPE_INT_RGB);
		Graphics  graphics = img.getGraphics();//得到画笔
		graphics.setFont(new Font("黑体", Font.CENTER_BASELINE,24));
		graphics.drawString(chars,20,27);
		graphics.dispose();//绘制结束
//		ImageIO.write(img,"JPG",new FileOutputStream("C:\\Users\\ad\\Desktop\\vue\\a.jpg"));//输出图片
		return img;
//		
	}
	/**
	 * 获取随机的字符集
	 * @return
	 */
	public String getRandomCharacter(){
		String str = "";//随机验证码
		for(int i=0;i<digit;i++){
			int ran = (int) (Math.random()*strs.length);
			str+=strs[ran];
		}
		return str;
	}
	/**
	 * 获取随机颜色
	 * @param fc
	 * @param bc
	 * @return
	 */
	public Color getRandColor(int fc, int bc){
		 Random random = new Random();
	        if (fc > 255) fc = 255;
	        if (bc > 255) bc = 255;
	        int r = fc + random.nextInt(bc - fc);
	        int g = fc + random.nextInt(bc - fc);
	        int b = fc + random.nextInt(bc - fc);
	        return new Color(r, g, b);
	}
	public static void main(String[] args) throws FileNotFoundException, IOException {
		RandomGraphic a = new RandomGraphic();
		a.createGraphic(a.getRandomCharacter());
//		System.out.println(a.getRandomCharacter());
//		System.out.println((int)(Math.random()*100));
	}
	
}

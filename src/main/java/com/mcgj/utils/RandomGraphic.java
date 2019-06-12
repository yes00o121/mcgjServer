package com.mcgj.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 随机绘制图形
 * @author ad
 *
 */
public class RandomGraphic {
	
	private static Logger log = LoggerFactory.getLogger(RandomGraphic.class);
	
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
	
	/**
	 * 在制定文件夹下随机获取一张图片写入制定文本内容
	 * @param content 内容
	 * @param filePath 文件路径
	 * @param width 宽度
	 * @param height 高度
	 */
	public BufferedImage createWrodImage(String content,String filePath){
		try {
//			String filePath = "D:\\mcgj\\myProject\\mcgjServer\\src\\main\\webapp\\resources\\home\\card_banner";
			File file = new File(filePath);
			if(!file.exists()){
				throw new RuntimeException("横幅文件夹不存在");
			}
			//横幅图片数量
			int imgCount = file.list().length;
			if(file.list().length == 0){
				throw new RuntimeException("横幅文件夹下没有图片");
			}
			int random = new Random().nextInt(imgCount);//随机一张图片
			String imgName = file.list()[random];
			file = new File(filePath + "\\" + imgName);
			BufferedImage readImage = ImageIO.read(file);//读取文件流
			BufferedImage bufferImage = new BufferedImage(readImage.getWidth(null), readImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
			Graphics graphics = bufferImage.getGraphics();//获取画笔
			graphics.drawImage(readImage.getScaledInstance(readImage.getWidth(null), readImage.getHeight(null),Image.SCALE_SMOOTH),0,0,null);
			graphics.setColor(Color.RED);
			graphics.setFont(new Font("微软雅黑",Font.ROMAN_BASELINE,64));
			graphics.drawString(content, graphics.getFontMetrics().stringWidth(content),bufferImage.getHeight()/2);
			graphics.dispose();//释放资源
			return bufferImage;
		} catch (IOException e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	public static void main(String[] args) {
		try {
			System.out.println("............");
			String filePath = "D:\\mcgj\\myProject\\mcgjServer\\src\\main\\webapp\\resources\\home\\card_banner";
			File file = new File(filePath);
			if(!file.exists()){
				throw new RuntimeException("横幅文件夹不存在");
			}
			//横幅图片数量
			int imgCount = file.list().length;
			if(file.list().length == 0){
				throw new RuntimeException("横幅文件夹下没有图片");
			}
			int random = new Random().nextInt(imgCount);//随机一张图片
			String imgName = file.list()[random];
			file = new File(filePath + "\\" + imgName);
			BufferedImage readImage = ImageIO.read(file);//读取文件流
			BufferedImage bufferImage = new BufferedImage(readImage.getWidth(), readImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics graphics = bufferImage.getGraphics();//获取画笔
			graphics.drawString("坂井泉水", 200, 300);
			graphics.dispose();//释放资源
			ImageIO.write(bufferImage, "test.jpg", new File("D:\\mcgj"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

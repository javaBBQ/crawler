/*
 *Project: glorypty-crawler
 *File: com.glorypty.image.ThumbnailatorImageUtil.java
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.image;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;

import net.coobird.thumbnailator.Thumbnails;

/**
 * @author Andy
 * @date 2015年7月6日
 * @version 1.0
 */
public class ThumbnailatorImageUtil {
	static final Logger logger = Logger.getLogger(ThumbnailatorImageUtil.class);
	
	/**
	 * 网络下载图片
	 * @author Andy
	 * @param url  图片url
	 * @param width 图片宽度
	 * @param height 图片高度
	 * @param outFile 图片保存路径
	 * @return
	 */
	public static boolean downToFile(URL url,int width,int height,String outFile){
		boolean isDown = true;
		try {
			Thumbnails.of(url).size(width,height).toFile(outFile);
		} catch (IOException e) {
			isDown = false;
			logger.info("DownLoad image has exception:"+e.getMessage());
		}
		return isDown;
	}
	
	
	public static String  rename(){
		 return String.valueOf(new Date().getTime()) + new DecimalFormat("000").format(new Random().nextInt(999));
	}
	
	
//	public static String generationFile(URL url){
//		String file = url.getFile();
//		return file;
//	}
	
	
	public static void main(String[] args) {
		try {
			URL url = new URL("http://csyor.qiniudn.com/wp-content/uploads/2014/05/2014051122550637.jpg");
			System.out.println(url.getFile());
			int width = 614;
			int height = 512;
			String outFile = "F:/image/"+rename()+".jpg";
			boolean f = downToFile(url, width, height, outFile);
			System.out.println(f);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}


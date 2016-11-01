/*
 *Project: glorypty-crawler
 *File: com.glorypty.excel.FileTask.java <2015年4月8日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.excel.sfda;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.xkshow.commons.logging.LoggerBase;
import cn.xkshow.io.excel.PoiHelper;
import cn.xkshow.io.file.FileHelper;
import cn.xkshow.util.DateUtil;

/**
 *
 * @Author hardy
 * @Date 2015年4月8日 上午11:03:18
 */
public class FileTask extends LoggerBase implements Runnable {
	private String path;
	private String currentDirectory;
	
	private Map<String, Object[]> mapTitle = new HashMap<String, Object[]>();
	private List<Object[]> lstContent = new ArrayList<Object[]>();
	
	public FileTask(String path) {
		this.path = path;
		currentDirectory = path.substring(path.lastIndexOf("\\")+1);
	}

	public void run() {
		File[] files = FileHelper.listFiles(path);
		if(null==files)
			return;
		
		this.logger.info(MessageFormat.format("线程{0}正在破译目录({1})：{2}", Thread.currentThread().getName(), files.length, path));
		for (File file: files) {
			try {
				if(!mapTitle.containsKey(currentDirectory))
					excTitles(file);
				excContents(file);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
		String pathExcel = MessageFormat.format("{0}\\{1}_{2}_{3}.xls", Constants.PATH_STORE, currentDirectory, String.valueOf(files.length), DateUtil.toUnsignedDateTimeMill(new Date()));
		if(PoiHelper.create(pathExcel, mapTitle.get(currentDirectory), lstContent,10000)){
			//移除成功文件
		}
	}
	
	private void excTitles(File file) throws IOException {
		Document doc = Jsoup.parse(file, "UTF-8");
		Elements contents = doc.select("tr");
		if(null==contents || contents.isEmpty())
			return;
		List<String> lstTitle = new ArrayList<String>();
		for (Element element : contents) {
			Elements nodeTds = element.getElementsByTag("td");
			if(nodeTds.size() != 2)
				continue;
			lstTitle.add(nodeTds.get(0).text());
		}
		mapTitle.put(currentDirectory, lstTitle.toArray());
	}
	
	private void excContents(File file) throws IOException {
		Document doc = Jsoup.parse(file, "UTF-8");
		Elements contents = doc.select("tr");
		if(null==contents || contents.isEmpty())
			return;
		List<Object> lst = new ArrayList<Object>();
		for (Element element : contents) {
			Elements nodeTds = element.getElementsByTag("td");
			if(nodeTds.size() != 2)
				continue;
			lst.add(nodeTds.get(1).text());
		}
		lstContent.add(lst.toArray());
	}
	
}

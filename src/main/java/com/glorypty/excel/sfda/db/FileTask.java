/*
 *Project: glorypty-crawler
 *File: com.glorypty.excel.sfda.db.FileTask.java <2015年5月21日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.excel.sfda.db;

import java.io.File;
import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.xkshow.commons.logging.LoggerBase;
import cn.xkshow.io.file.FileHelper;

import com.glorypty.crawler.sfda.TempDB;
import com.glorypty.jdbc.CrawlerService;

/**
 *
 * @Author hardy 
 * @Date 2015年5月21日 下午4:25:27
 * @version 1.0
 */
public class FileTask extends LoggerBase implements Runnable {
	private static final String TXT = "<table width='100%' align='center'>{0}</table>";
	private String path = null;
	private String currentDirectory = null;
	
	public FileTask(String path) {
		this.path = path;
		currentDirectory = path.substring(path.lastIndexOf("\\")+1);
	}

	@Override
	public void run() {
		File[] files = FileHelper.listFiles(path);	
		if(null==files)
			return;
		this.logger.info(MessageFormat.format("线程{0}正在破译目录({1})：{2}", Thread.currentThread().getName(), files.length, path));
		for (File file: files) {
			try {								
				Document doc = Jsoup.parse(file, "GBK");
				Elements contents = null!=doc ? doc.select("table[align=center]") : null;	
				if(null!=contents && !contents.isEmpty()){	
					Element element = contents.get(0);
					this.saveDB(TempDB.DATA_GROUP_NAME.get(currentDirectory), element);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
	}
	
	private void saveDB(String tableId, Element element){
		Elements nodesTR = element.select("tr:not(tr[^bgc])");
		if(null==nodesTR || nodesTR.isEmpty() || nodesTR.size()<2){
			return;
		}
		// 获取分类组装标题
		String title = TempDB.getTitleByTable(tableId, nodesTR.select("td"));
		if(StringUtils.isNotEmpty(title)){
			CrawlerService.executeDataBank(TempDB.DATA_GROUP.get(tableId), title, MessageFormat.format(TXT, nodesTR.toString().replaceAll("\"", "'")));	
		}
	}
}

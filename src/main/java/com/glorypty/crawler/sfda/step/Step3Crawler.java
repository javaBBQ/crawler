/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.sfda.step.Step3Crawler.java	<2015年4月2日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.sfda.step;

import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.xkshow.io.file.FileHelper;
import cn.xkshow.net.URLHelper;

import com.glorypty.crawler.sfda.Constants;
import com.glorypty.crawler.sfda.StepCrawler;

import edu.uci.ics.crawler4j.crawler.Page;

/**
 * 采集详情页内容 by 详情页
 * @Author hardy
 * @Date 2015年4月2日 下午3:55:22
 */
public class Step3Crawler extends StepCrawler {
	
	@Override
	public void visit(Page page) {
		String url = page.getWebURL().getURL();
		this.logger.debug("执行：详情页：" + url);
		try {
			Document doc = this.getDocument(page);
			if (null != doc) {				
				Elements contents = doc.select("div table[align=center]");				
				if(null!=contents && !contents.isEmpty()){
					Element element = contents.get(0);
					Long id = Long.valueOf(URLHelper.getValue(url, "Id"));
					String filename = id + ".htm";
					// 构建文件生成目录
					String dir = element.select("div.zs2").text();					
					if(StringUtils.isEmpty(dir)){
						this.logger.error("解析：详情页 : "+ url);
						return;
					}
					String path = MessageFormat.format("{0}step3/data/{1}/", Constants.CRAWL_STORAGE, dir);
					
					// 文件不存在，且创建成功时写入文件
					if(!FileHelper.isExist(path+filename) && FileHelper.createAuto(path, filename)){	
						this.logger.debug("生成：详情页 : "+ path + filename);
						FileHelper.write(path+filename, MessageFormat.format(Constants.CRAWLER_DOWNLOAD_CONTENT, Constants.HTML_CHARSET, element.toString()).getBytes());
					}
				}
			}
		} catch (Exception e) {
			this.logger.error("执行：详情页("+ url +")： " + e);
		}
	}

}

/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.base.BaseCrawler.java  <2015年4月14日>
 ****************************************************************
 * 版权所有@2015 国裕秀网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.base;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;

/**
 * @Author hardy 
 * @Date 2015年4月14日 下午8:15:54
 * @version 1.0
 */
public class BaseCrawler extends WebCrawler {
	protected final Logger logger = Logger.getLogger(this.getClass());	

	/**
	 * 获取爬取页Jsoup文档
	 * @param page
	 * @return
	 * @Author hardy
	 * 2015年4月4日 下午1:45:05
	 */
	public Document getDocument(Page page){
		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			return Jsoup.parse(htmlParseData.getHtml());
		}
		return null;
	}
}

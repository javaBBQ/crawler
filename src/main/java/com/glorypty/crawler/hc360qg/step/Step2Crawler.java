/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.hc360.step.Step1Crawler.java <2015年12月17日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.hc360qg.step;

import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.hc360qg.Hc360QgConstants;
import com.glorypty.crawler.utils.ProcessDate;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * 爬取所有列表页，获取商品详情页链接
 * @author ZhangLujun 
 * @Date 2015年12月17日 下午2:17:44
 * @version 1.0
 */
public class Step2Crawler extends BaseCrawler {

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		if(super.shouldVisit(page, url)){
			return true;
		}
		return false;
	}

	@Override
	public void visit(Page page) {
		
		try {
			this.getThread().sleep(10000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		String href = page.getWebURL().getURL();
		this.logger.info("聪慧网-解析列表分页,获取详情页链接:"+href);
		
		List<String> urlList = new LinkedList<String>();
		try {
			Document doc = this.getDocument(page);
			if(doc!=null){
				Elements page_elements = doc.select("div.cont-left>div.buysbox");
				if(!page_elements.isEmpty()){
					for (Element element : page_elements) {
						//日期
						String dateStr = element.select("div.tileft.day").text();
						//比较日期-指定起始日期
						if(ProcessDate.compareDateDay(ProcessDate.convertToDate(dateStr,"yyyy-MM-dd"),Hc360QgConstants.getPastDay())){
							String url = element.select("div.tileft.txtcont>ol>li>h3.titlelist>a").attr("href");
							urlList.add(url);
						}
					}
				}
			}
			
			new Step3Controller().door(urlList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

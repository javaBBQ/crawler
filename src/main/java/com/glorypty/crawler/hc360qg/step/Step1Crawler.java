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

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * 爬取所有列表页，获取商品详情页链接
 * @author ZhangLujun 
 * @Date 2015年12月17日 下午2:17:44
 * @version 1.0
 */
public class Step1Crawler extends BaseCrawler {

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
		this.logger.info("聪慧网-解析列表分页链接:"+href);
		
		List<String> urlList = new LinkedList<String>();
		try {
			Document doc = this.getDocument(page);
			int pageTotal = 0;
			if(doc!=null){
				Elements pagetotal_elements = doc.select("div.s-mod-page>span.total");
				Elements pages_elements = doc.select("div.s-mod-page>a");
				//至少不少于10页分页数
				if(!pagetotal_elements.isEmpty()){
					pageTotal = Integer.parseInt(pagetotal_elements.text().substring(1, pagetotal_elements.text().length()-1));
					String pageUrl = pages_elements.get(0).attr("href");
					for (int i = 0; i < pageTotal; i++) {
						pageUrl = getPageUrl(pageUrl, "&", "ee=", i+1);
						urlList.add(pageUrl);
					}
				}else{
					//分页数在1到10页之间
					if(!pages_elements.isEmpty()){
						for (Element element : pages_elements) {
							urlList.add(element.attr("href"));
						}
					}
					//分页数为1页或没有
					else{
						urlList.add(href);
					}
				}
			}
			
			new Step2Controller().door(urlList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getPageUrl(String pageUrl,String separation,String str,int pageNum){
		String result = "";
		int i = 0;
		String[] arr = pageUrl.split(separation);
		for (String value : arr) {
			if(value.indexOf(str)!=-1){
				result += str+pageNum;
			}else{
				result +=value;
			}
			i++;
			if(i<arr.length){
				result +="&";
			}
		}
		return result;
	}
	
}

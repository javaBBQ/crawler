/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.drug39.step.DrugStep0Crawler.java
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.drug39.step;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.drug39.DrugConstants;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * @author Andy
 * @date 2015年6月24日
 * @version 1.0
 */
public class DrugStep0Crawler extends BaseCrawler {

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		return true;
	}	
	@Override
	public void visit(Page page) {
		visitToHome(page);
	}
	
	
	/**
	 * @author Andy
	 * @param page
	 */
	private void visitToHome(Page page) {
		List<String> urlList = new ArrayList<String>(); 
		String href = page.getWebURL().getURL();
		this.logger.info("39健康网解析首页："+href);
		Document doc = this.getDocument(page);
		int pageNum = 0;
		if(doc!=null){
			Elements elements = doc.select("div.list_page>span>a");
			if(elements!=null && !elements.isEmpty()){
				String link = elements.get(1).attr("href");
				String totalPage = link.substring(link.indexOf("_")+1, link.indexOf(".html"));
				pageNum = Integer.parseInt(totalPage);
			}
			urlList = getPageList(href,pageNum);
			DrugConstants.totalPage = pageNum+1;
		}
		
		if(urlList!=null && urlList.size()>0){
			try {
				new DrugStep1Controller().door(urlList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * @author Andy
	 * @param href
	 * @param pageNum
	 */
	private List<String> getPageList(String href, int pageNum) {
		List<String> urlList = new ArrayList<String>();
		if(pageNum==0){
			urlList.add(href+"index.html");
		}else{
			for (int i = 0; i <=pageNum; i++) {
				String url = "";
				if(i==0){
					url = href+"index.html";
				}else{
					url = href + "index_"+i+".html";
				}
				urlList.add(url);
			}
		}
		return urlList;
	}

}


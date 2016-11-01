/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.drug39.step.DrugStep1Crawler.java
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.drug39.step;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
public class DrugStep1Crawler extends BaseCrawler {

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		String href = url.getURL();
		if(href.indexOf(DrugConstants.RDFT_HYGC_URL_HOME)!=-1){
			return true;
		}else if(href.indexOf(DrugConstants.RDFT_XYDT_URL_HOME)!=-1){
			return true;
		}else if(href.indexOf(DrugConstants.RDFT_YQDT_URL_HOME)!=-1){
			return true;
		}else if(href.indexOf(DrugConstants.RDFT_YYXW_URL_HOME)!=-1){
			return true;
		}
		return false;
	}

	@Override
	public void visit(Page page) {
		String href = page.getWebURL().getURL();
		this.logger.info("39健康网解析列表页："+href);
		visitToList(page);
	}
	

	/**
	 * @author Andy
	 * @param page
	 */
	private void visitToList(Page page) {
		boolean isFinish = false;
		String href = page.getWebURL().getURL();
		Document doc = this.getDocument(page);
		if(doc!=null){
			Elements elements = doc.select("div.listbox>ul>li>span.text>a");
			if(elements!=null && !elements.isEmpty()){
				for (Element element : elements) {
					String url =  element.attr("href");
					DrugConstants.detailList.add(url);
				}
			}
			
			int current = 1;
			Elements current_elements = doc.select("div.list_page>b");
			if(current_elements!=null && !current_elements.isEmpty()){
				String currentPage = current_elements.get(0).text();
				current = Integer.parseInt(currentPage);
			}
			
			if(current == DrugConstants.totalPage){
				isFinish = true;
			}
		}
		
		
		if(isFinish){
			if(DrugConstants.detailList!=null && DrugConstants.detailList.size()>0){
				try {
					new DrugStep2Controller().door(DrugConstants.detailList);
					DrugConstants.detailList.clear();
					this.myController.shutdown();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}


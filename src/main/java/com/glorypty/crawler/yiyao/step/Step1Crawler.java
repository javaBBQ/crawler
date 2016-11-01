/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.yiyao.stepStep1Crawler.java
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.yiyao.step;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.utils.ProcessDate;
import com.glorypty.crawler.yiyao.YiYaoConstants;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * @author Andy
 * @date 2015年6月16日
 * @version 1.0
 */
public class Step1Crawler extends BaseCrawler {
	
	@Override
	public boolean shouldVisit(Page page, WebURL url) {
//		return super.shouldVisit(page, url);
		return true;
	}

	@Override
	public void visit(Page page) {
		String href = page.getWebURL().getURL();
		if(href.indexOf(YiYaoConstants.ZCFG_PAGE_URL_HOME_PRE)!=-1
				||href.indexOf(YiYaoConstants.SHGZ_PAGE_URL_HOME_PRE)!=-1
				||href.indexOf(YiYaoConstants.YYXW_PAGE_URL_HOME_PRE)!=-1
				||href.indexOf(YiYaoConstants.YYQYXW_PAGE_URL_HOME_PRE)!=-1
				||href.indexOf(YiYaoConstants.YYZTB_PAGE_URL_HOME_PRE)!=-1
				||href.indexOf(YiYaoConstants.ZH_PAGE_URL_HOME_PRE)!=-1
				||href.indexOf(YiYaoConstants.YYYX_PAGE_URL_HOME_PRE)!=-1
				||href.indexOf(YiYaoConstants.YYZBZB_PAGE_URL_HOME_PRE)!=-1){
			visitToCommentPage(page);
		}else if(href.indexOf(YiYaoConstants.DLXX_PAGE_URL_HOME_PRE)!=-1){
			visitToDlPage(page);
		}else if(href.indexOf(YiYaoConstants.YYZS_PAGE_URL_HOME_PRE)!=-1){
			visitToXZsPage(page);
		}
	}

	/**
	 * 招商
	 * @author Andy
	 * @param page
	 */
	private void visitToXZsPage(Page page) {
		boolean isFinish = false;
		String href = page.getWebURL().getURL();
		this.logger.info("100医药网解析招商列表页："+href);
		Document doc = this.getDocument(page);
		if(doc!=null){
			Elements elements = doc.select("div.smenu>div.list>ul>li");
			if(elements!=null && !elements.isEmpty()){
				for (Element element : elements) {
					String url = element.getElementsByAttributeValue("class", "green").attr("href");
					String date = element.getElementsByAttributeValue("class", "ptime").text();
					date = date.substring(date.indexOf("更新时间")+5).trim();
					if(ProcessDate.compareDate(date,"yyyy/MM/DD HH:mm:ss", YiYaoConstants.PAST_MONTH)){
						YiYaoConstants.detail_list.add(YiYaoConstants.HOMR_URL+url);
					}else{
						isFinish = true;
						break;
					}
				}
			}
		}
		
		if(isFinish){
			try {
				if( YiYaoConstants.detail_list!=null && YiYaoConstants.detail_list.size()>0){
					new Step2Controller().door(YiYaoConstants.detail_list);
					YiYaoConstants.detail_list.clear();
				}
				this.getMyController().shutdown();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 代理
	 * @author Andy
	 * @param page
	 */
	private void visitToDlPage(Page page) {
		boolean isFinish = false;
		String href = page.getWebURL().getURL();
		this.logger.info("100医药网解析代理列表页："+href);
		Document doc = this.getDocument(page);
		if(doc!=null){
			Elements elements = doc.select("table.yd>tbody>tr[bgcolor=#FFFFFF]");
			if(elements!=null&&!elements.isEmpty()){
				for (Element element : elements) {
					String url = element.getElementsByAttributeValue("target", "_blank").attr("href");
					String str = element.getElementsByAttributeValue("bgcolor", "#FFFFFF").get(2).text();
					if(ProcessDate.compareDate(str.trim(),"yyyy-MM-DD", YiYaoConstants.PAST_MONTH)){
						YiYaoConstants.detail_list.add(YiYaoConstants.HOMR_URL+url);
					}else{
						isFinish = true;
						break;
					}
				}
			}
		}
		
		if(isFinish){
			try {
				if( YiYaoConstants.detail_list!=null && YiYaoConstants.detail_list.size()>0){
					new Step2Controller().door(YiYaoConstants.detail_list);
					YiYaoConstants.detail_list.clear();
				}
				this.getMyController().shutdown();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @author Andy
	 * @param doc
	 * @param href
	 */
	private void visitToCommentPage(Page page) {
		boolean isFinish = false;
		String href = page.getWebURL().getURL();
		this.logger.info("100医药网解析资讯列表页："+href);
		Document doc = this.getDocument(page);
		if(doc!=null){
			Elements elements = doc.select("div.list>ul>li");
			if(elements!=null&&!elements.isEmpty()){
				if(href.indexOf(YiYaoConstants.YYYX_PAGE_URL_HOME_PRE)!=-1){
					for (Element element : elements) {
						String url = element.getElementsByAttributeValue("class", "u").attr("href");
						YiYaoConstants.detail_list.add(YiYaoConstants.URL_CONTENT_PRE+url);
					}
				}else if(href.indexOf(YiYaoConstants.ZH_PAGE_URL_HOME_PRE)!=-1){
					for (Element element : elements) {
						String url = element.getElementsByAttributeValue("class", "u").attr("href");
						String str = element.text();
						if(str.indexOf("-")!=-1){
							str = str.substring(str.indexOf("(")+1, str.indexOf(")"));
							if(ProcessDate.compareDate(YiYaoConstants.fixedDate,str,"yyyy-MM-DD")){
								YiYaoConstants.detail_list.add(YiYaoConstants.URL_CONTENT_PRE+url);
							}else{
								isFinish = true;
								break;
							}
						}
					}
				}else{
					for (Element element : elements) {
						String url = element.getElementsByAttributeValue("class", "u").attr("href");
						String str = element.text();
						if(str.indexOf("-")!=-1){
							str = str.substring(str.indexOf("(")+1, str.indexOf(")"));
							if(ProcessDate.compareDate(str,"yyyy-MM-DD", YiYaoConstants.PAST_MONTH)){
								YiYaoConstants.detail_list.add(YiYaoConstants.URL_CONTENT_PRE+url);
							}else{
								isFinish = true;
								break;
							}
						}
					}
				}
				
				if(YiYaoConstants.detail_list!=null && YiYaoConstants.detail_list.size()>0){
					if((href.indexOf("/1.html")!=-1 || href.indexOf("/0,1.html")!=-1)){
						Elements page_elements = doc.select("div.page>b");
						if(page_elements!=null&&!page_elements.isEmpty()){
							int pageNum = Integer.parseInt(page_elements.get(1).text());
							if(pageNum==1)
								isFinish = true;
						}
					}else{
						Elements page_elements = doc.select("div.page>b");
						if(page_elements!=null&&!page_elements.isEmpty()){
							int pageNum = Integer.parseInt(page_elements.get(1).text());
							int currentPage = Integer.parseInt(page_elements.get(page_elements.size()-1).text());
							if(pageNum==currentPage)
								isFinish = true;
						}
					}
				}
			}
			
			if(isFinish){
				try {
					if( YiYaoConstants.detail_list!=null && YiYaoConstants.detail_list.size()>0){
						new Step2Controller().door(YiYaoConstants.detail_list);
						YiYaoConstants.detail_list.clear();
					}
					this.getMyController().shutdown();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}


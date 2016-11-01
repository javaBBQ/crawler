/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.hc360.step.Step1Crawler.java <2015年12月17日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.hc360.step;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.hc360.Hc360Constants;

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
			String href = url.getURL();
			if(href.indexOf(Hc360Constants.BJP_PRD_LIST_PR_URL)!=-1){
				return true;
			}
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
		
		try {
//			Hc360Constants.prd_deatil_url_list.clear();
			//保健品
			if(href.indexOf(Hc360Constants.BJP_PRD_LIST_PR_URL)!=-1){
				deal(page);
				if(href.indexOf(Hc360Constants.bjp_total+"")!=-1 && href.indexOf("af=2")!=-1){
					new Step2Controller().door(Hc360Constants.bjp_deatil_url_list);
					this.logger.info("====保健品列表页分析完成======");
				}
			}
			//医疗器械
			else if(href.indexOf(Hc360Constants.YLQX_PRD_LIST_PR_URL)!=-1){
				deal(page);
				if(href.indexOf(Hc360Constants.ylqx_total+"")!=-1 && href.indexOf("af=2")!=-1){
					new Step2Controller().door(Hc360Constants.ylqx_deatil_url_list);
					this.logger.info("====医疗器械列表页分析完成======");
				}
			}
			//药品
			else if((href.indexOf(Hc360Constants.YP_PRD_LIST_PR_URL)!=-1
					||href.indexOf(Hc360Constants.YP_PRD_LIST_PR_URL2)!=-1)
					&& href.indexOf(Hc360Constants.endChar)!=-1){
				deal(page);
				if(href.indexOf(Hc360Constants.yp_total+"")!=-1 && href.indexOf("af=2")!=-1){
					new Step2Controller().door(Hc360Constants.yp_deatil_url_list);
					this.logger.info("====药品列表页分析完成======");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void deal(Page page){
		String href = page.getWebURL().getURL();
		Document doc = this.getDocument(page);
		if(doc!=null){
			
//			Elements elements = doc.select("li.grid-list>div.item>div.picmid>a");
//			if(elements!=null && !elements.isEmpty()){
//				for (Element element : elements) {
//					String url = element.getElementsByAttribute("href").attr("href");
//					System.out.println(url);
//					if(url.indexOf(Hc360Constants.PRD_DETAIL_PR_URL)!=-1){
//						Hc360Constants.prd_deatil_url_list.add(url);
//					}
//				}
//			}
			
			if(href.indexOf(Hc360Constants.BJP_PRD_LIST_PR_URL)!=-1){
				Elements elements = doc.select("li.grid-list>div.item>div.picmid>a");
				if(elements!=null && !elements.isEmpty()){
					for (Element element : elements) {
						String url = element.getElementsByAttribute("href").attr("href");
						this.logger.info(url);
						if(url.indexOf(Hc360Constants.PRD_DETAIL_PR_URL)!=-1){
							Hc360Constants.bjp_deatil_url_list.add(url);
						}
					}
				}	
			}else if(href.indexOf(Hc360Constants.YLQX_PRD_LIST_PR_URL)!=-1){
				Elements elements = doc.select("li.grid-list>div.item>div.picmid>a");
				if(elements!=null && !elements.isEmpty()){
					for (Element element : elements) {
						String url = element.getElementsByAttribute("href").attr("href");
						this.logger.info(url);
						if(url.indexOf(Hc360Constants.PRD_DETAIL_PR_URL)!=-1){
							Hc360Constants.ylqx_deatil_url_list.add(url);
						}
					}
				}	
			}else if((href.indexOf(Hc360Constants.YP_PRD_LIST_PR_URL)!=-1
					||href.indexOf(Hc360Constants.YP_PRD_LIST_PR_URL2)!=-1)
					&& href.indexOf(Hc360Constants.endChar)!=-1){
				Elements elements = doc.select("li.grid-list>div.item>div.picmid>a");
				if(elements!=null && !elements.isEmpty()){
					for (Element element : elements) {
						String url = element.getElementsByAttribute("href").attr("href");
						this.logger.info(url);
						if(url.indexOf(Hc360Constants.PRD_DETAIL_PR_URL)!=-1){
							Hc360Constants.yp_deatil_url_list.add(url);
						}
					}
				}	
			}
		}
	}
}

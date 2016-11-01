/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.hc360.step.Step0Crawler.java <2015年12月17日>
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
import com.glorypty.crawler.hc360.Hc360Constants;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * 爬虫入口程序：爬取列表首页，获取列表分页链接
 * @author ZhangLujun 
 * @Date 2015年12月17日 上午11:02:03
 * @version 1.0
 */
public class Step0Crawler extends BaseCrawler {
	
	public int page = 1;
	public static List<String> list = null;
	
	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		if(super.shouldVisit(page, url)){
			String href = url.getURL();
			List<String> list = Hc360Constants.HOME_LIST_URL;
			if(list!=null && list.size()>0){
				for (String str : list) {
					if(href.indexOf(str)!=-1){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public void visit(Page page) {
		try {
			String href = page.getWebURL().getURL();
			this.logger.info("聪慧网抓取求购信息首页链接:"+href);
			
			Document doc = this.getDocument(page);
			List<String> urlList = new LinkedList<String>();
			if(doc!=null){
				String type = "";
				Elements elements = doc.select("table#tabstyle1>tbody>tr>td");
				if(elements!=null&&!elements.isEmpty()){
					for (Element element : elements) {
						type = element.text();
						if(type.trim().equals("制药")){
							System.out.println(type);
							continue;
						}else if(type.trim().equals("西药")){
							System.out.println(type);
							continue;
						}else if(type.trim().equals("保健品")){
							System.out.println(type);
							continue;
						}else if(type.trim().equals("医疗器械")){
							System.out.println(type);
							continue;
						}
						
						Elements elements2 = element.getElementsByTag("strong");
						if (!elements2.isEmpty()) {
							Element element2 = elements2.get(0);
							Elements elements3 = element2.getElementsByTag("a");
							urlList.add(elements3.attr("href"));
						}
					}
				}
				
				new Step1Controller().door(urlList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

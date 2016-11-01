package com.glorypty.crawler.qgyyzs.step;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.qgyyzs.QgyyzsConstants;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

public class QgyyzsStep0Crawler extends BaseCrawler {
	
	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		String href = url.getURL();
		List<String> list = QgyyzsConstants.getUrlList();
		for (String home : list) {
			if(href.equals(home)){
				return true;
			}
		}
		return false;
	}

	@Override
	public void visit(Page page) {
		String href = page.getWebURL().getURL();
		
		List<String> urlList = new ArrayList<String>();
		String listUrl = null;
		Document doc = getDocument(page);
		String pageStr = null;
		int maxPageSize = 0;
		
		
		if(doc != null){
			
			//取最大页数
			Elements elements = null;
			QgyyzsConstants.listUrlSize = 0;
			if(href.equals(QgyyzsConstants.ZHLT_URL_HOME)){
				logger.info("环球医院网解析首页:"+href);
				elements = doc.select("div[class=turn_page] a");
				//http://www.qgyyzs.net/zhanhui/zh0_95.htm 
				pageStr = elements.get(elements.size() - 2).attr("href").replaceAll("\\D", "");
				maxPageSize = Integer.parseInt(pageStr);
				for(int i = 1; i <= QgyyzsConstants.MAX_PAGE_SIZE && i<=maxPageSize; i++){
					listUrl = QgyyzsConstants.ZHLT_LIST_PRE + i + ".htm";
					urlList.add(listUrl);
					QgyyzsConstants.listUrlSize++;
				}
			}else if(href.equals(QgyyzsConstants.ZBXX_URL_HOME)){
				logger.info("环球医院网解析首页:"+href);
				elements = doc.select("div[class=turn_page] a");
				// http://zb.qgyyzs.net/zhaobiao_list.asp?title=&language_dd=&language_sj=1&topage=22
				pageStr =  elements.get(elements.size() - 2).attr("href").replaceAll(".*topage=", "");
				maxPageSize = Integer.parseInt(pageStr);
				for(int i = 1; i <= QgyyzsConstants.MAX_PAGE_SIZE && i<=maxPageSize; i++){
					listUrl = QgyyzsConstants.ZBXX_LIST_PRE + i;
					urlList.add(listUrl);
					QgyyzsConstants.listUrlSize++;
				}
			}else if(href.equals(QgyyzsConstants.QGXX_URL_HOME)){
				logger.info("环球医院网解析首页:"+href);
				// http://zb.qgyyzs.net/zhaobiao_list.asp?title=&language_dd=&language_sj=1&topage=22
				pageStr =  doc.select("a[title=末页]").get(0).attr("href").replaceAll("\\D", "");
				maxPageSize = Integer.parseInt(pageStr);
				for(int i = 1; i <= QgyyzsConstants.MAX_PAGE_SIZE && i<=maxPageSize; i++){
					listUrl = QgyyzsConstants.QGXX_LIST_PRE + i + ".htm";
					urlList.add(listUrl);
					QgyyzsConstants.listUrlSize++;
				}
			}
			
			try {
				if(!urlList.isEmpty() && urlList.size()>0){
					new QgyyzsStep1Controller().door(urlList);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			
	}

	
}

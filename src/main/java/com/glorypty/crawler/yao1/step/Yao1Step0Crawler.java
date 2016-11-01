package com.glorypty.crawler.yao1.step;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.yao1.Yao1Constants;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

public class Yao1Step0Crawler extends BaseCrawler {
	
	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		boolean flag = false;
		if(super.shouldVisit(page, url)){
			String href = url.getURL();
			List<String> list = Yao1Constants.getUrlList();
			for (String home : list) {
				if(href.startsWith(home)){
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	@Override
	public void visit(Page page) {
		String href = page.getWebURL().getURL();
		logger.info("医药公信网解析首页:"+href);
		Document doc = getDocument(page);
		if(doc != null){
			String pageStr = doc.select("span[class=f_L blue txt_R H_TP10]").html().replaceAll("\\D", "").substring(1);
			int maxPageSize = Integer.parseInt(pageStr);
			List<String> urlList = new ArrayList<String>();
			
			for(int i=1; i <= Yao1Constants.MAX_PAGE_SIZE && i <= maxPageSize; i++){
				if(href.contains(Yao1Constants.RDHT_URL_HOME)){
					urlList.add(Yao1Constants.RDHT_LIST_PRE + i);
				}else{
					urlList.add(Yao1Constants.JYFT_LIST_PRE + i);
				}
				Yao1Constants.listUrlSize++;
			}
			
			try {
				new Yao1Step1Controller().door(urlList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			
	}
	
}

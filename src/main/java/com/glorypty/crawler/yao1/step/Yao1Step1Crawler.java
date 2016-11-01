package com.glorypty.crawler.yao1.step;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.yao1.Yao1Constants;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

public class Yao1Step1Crawler extends BaseCrawler {

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		if(super.shouldVisit(page, url)){
			return true;
		}
		return false;
	}

	@Override
	public void visit(Page page) {
		
		String href = page.getWebURL().getURL();
		this.logger.info("医药公信网解析列表页："+href);
		//解析一个列表页删除一个
		Yao1Constants.listUrlSize--;
		
		Document doc = getDocument(page);
		
		if(doc != null){
			Elements elements = doc.select("dt[class=gray] a[title]");
			for (Element e : elements) {
				Yao1Constants.detailUrls.add(Yao1Constants.URL_HOME + e.attr("href"));
			}
		}
		
		if(Yao1Constants.listUrlSize <= 0){
			try {
				if(Yao1Constants.detailUrls != null && Yao1Constants.detailUrls.size() > 0){
					new Yao1Step2Controller().door(Yao1Constants.detailUrls);
					Yao1Constants.detailUrls.clear();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.getMyController().shutdown();
		}
		
	}
	
}

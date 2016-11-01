package com.glorypty.crawler.yaolutong.step;

import java.util.List;

import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.common.ConstantsCrawler;

import edu.uci.ics.crawler4j.crawler.CrawlController;

public class YaolutongStep2Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		if(null==lstUrls || lstUrls.isEmpty())
			return;
//		CrawlController crawlController = this.getController(0);
		CrawlController crawlController = this.getController(ConstantsCrawler.CRAWL_STORAGE + "yaolutong/", true,0);
		for(String link : lstUrls){
			crawlController.addSeed(link);
		}
		lstUrls.clear();
		
		/*CrawlController crawlController = this.getController(0);
		crawlController.addSeed("http://www.yaolutong.com/zhaoshang/single.aspx?id=38170");*/
		crawlController.start(YaolutongStep2Crawler.class, 1);
	}

	@Override
	protected void doorError() {
		
	}

}

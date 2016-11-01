package com.glorypty.crawler.baiduyy.step;

import java.util.List;

import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.common.ConstantsCrawler;

import edu.uci.ics.crawler4j.crawler.CrawlController;

public class BaiduyyStep1Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		if(null==lstUrls || lstUrls.isEmpty())
			return;
		//本地
//		CrawlController crawlController = this.getController(1);
		CrawlController crawlController = this.getController(ConstantsCrawler.CRAWL_STORAGE + "baiduyy/", true,1);
		for(String link : lstUrls){
			crawlController.addSeed(link);
		}
		lstUrls.clear();
//		CrawlController crawlController = this.getController(1);
//		crawlController.addSeed("http://www.baiduyy.com/dailishang/index.aspx?page=1&jicount=36454");
//		crawlController.addSeed("http://www.baiduyy.com/dailishang/index.aspx?page=2&jicount=36454");
		crawlController.start(BaiduyyStep1Crawler.class, 1);
	}

	@Override
	protected void doorError() {
		
	}

}

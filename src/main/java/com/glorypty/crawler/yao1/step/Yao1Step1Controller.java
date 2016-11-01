package com.glorypty.crawler.yao1.step;

import java.util.List;

import com.glorypty.crawler.base.BaseController;

import edu.uci.ics.crawler4j.crawler.CrawlController;

public class Yao1Step1Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		if(null==lstUrls || lstUrls.isEmpty())
			return;
		//本地
		CrawlController crawlController = this.getController(0);
		
		for(String link : lstUrls){
			crawlController.addSeed(link);
		}
		lstUrls.clear();
//		CrawlController crawlController = this.getController(1);
		crawlController.start(Yao1Step1Crawler.class, 1);
	}

	@Override
	protected void doorError() {
		
	}

}

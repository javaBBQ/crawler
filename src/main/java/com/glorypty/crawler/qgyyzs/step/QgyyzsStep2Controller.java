package com.glorypty.crawler.qgyyzs.step;

import java.util.List;

import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.common.ConstantsCrawler;

import edu.uci.ics.crawler4j.crawler.CrawlController;

public class QgyyzsStep2Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		if(null==lstUrls || lstUrls.isEmpty())
			return;
		//本地
//		CrawlController crawlController = this.getController(0);
		CrawlController crawlController = this.getController(ConstantsCrawler.CRAWL_STORAGE + "qgyyzs/", true,0);
		for(String link : lstUrls){
			crawlController.addSeed(link);
		}
		lstUrls.clear();
		crawlController.start(QgyyzsStep2Crawler.class, 1);
	}

	@Override
	protected void doorError() {
		
	}

}

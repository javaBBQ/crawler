package com.glorypty.crawler.qgyyzs.step;

import java.util.List;

import com.glorypty.crawler.base.BaseController;
import edu.uci.ics.crawler4j.crawler.CrawlController;

public class QgyyzsStep1Controller extends BaseController {

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
		crawlController.start(QgyyzsStep1Crawler.class, 1);
	}

	@Override
	protected void doorError() {
		
	}

}

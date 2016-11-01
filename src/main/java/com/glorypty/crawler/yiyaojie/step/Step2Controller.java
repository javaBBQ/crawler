package com.glorypty.crawler.yiyaojie.step;

import java.util.List;

import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.common.ConstantsCrawler;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * 
 * @author yiwen
 *
 */
public class Step2Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		if(null==lstUrls || lstUrls.isEmpty())
			return;
		
		CrawlController crawlController = this.getController(ConstantsCrawler.CRAWL_STORAGE + "yiyaojie/", true, 0);
		for(String link : lstUrls){
			crawlController.addSeed(link);
		}
		lstUrls.clear();
		crawlController.start(Step2Crawler.class, 1);

	}

	@Override
	protected void doorError() {
		// TODO Auto-generated method stub

	}

}

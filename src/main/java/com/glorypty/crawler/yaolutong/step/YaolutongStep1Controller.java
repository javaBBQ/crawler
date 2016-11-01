package com.glorypty.crawler.yaolutong.step;

import java.util.List;

import com.glorypty.crawler.alibaba.step.Step3Crawler;
import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.common.ConstantsCrawler;

import edu.uci.ics.crawler4j.crawler.CrawlController;

public class YaolutongStep1Controller extends BaseController {

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
		crawlController.start(YaolutongStep1Crawler.class, 1);
	}

	@Override
	protected void doorError() {
		
	}

}

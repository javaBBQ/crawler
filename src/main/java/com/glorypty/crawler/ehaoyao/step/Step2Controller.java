package com.glorypty.crawler.ehaoyao.step;

import java.util.List;

import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.common.ConstantsCrawler;
import com.glorypty.crawler.ehaoyao.EHaoYaoConstants;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * 
 * @author liuj
 *
 */
public class Step2Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		if(null==lstUrls || lstUrls.isEmpty())
			return;
		
		CrawlController crawlController = this.getController(ConstantsCrawler.CRAWL_STORAGE + EHaoYaoConstants.RECORD_DIR, true, 0);
//		CrawlController crawlController = this.getController(0);
		for(String link : lstUrls){
			crawlController.addSeed(link);
		}
		lstUrls.clear();
		crawlController.start(Step2Crawler.class, 1);
	}

	@Override
	protected void doorError() {
	}
	
}

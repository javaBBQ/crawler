package com.glorypty.crawler.yiyao.step;

import java.util.List;

import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.common.ConstantsCrawler;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * 
 * @author ZhangLujun
 *
 */
public class Step2Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		if(null==lstUrls || lstUrls.isEmpty())
			return;
		
//		CrawlController crawlController = this.getController(0);
		CrawlController crawlController = this.getController(ConstantsCrawler.CRAWL_STORAGE + "yiyao100/", true, 0);
		for(String link : lstUrls){
			crawlController.addSeed(link);
		}
		lstUrls.clear();
//		for (int i = 1; i < 100; i++) {
//			crawlController.addSeed(MessageFormat.format(YiYaoConstants.DLXX_PAGE_URL_HOME, i));
//		}
		crawlController.start(Step2Crawler.class, 1);

	}

	@Override
	protected void doorError() {
		// TODO Auto-generated method stub

	}

}

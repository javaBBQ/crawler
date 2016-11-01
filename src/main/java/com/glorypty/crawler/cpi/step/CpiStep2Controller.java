package com.glorypty.crawler.cpi.step;

import java.util.List;

import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.common.ConstantsCrawler;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * 
 * @author Zhanglujun
 *
 */
public class CpiStep2Controller extends BaseController{

	@Override
	public void door(List<String> lstUrls) throws Exception {
		if(null==lstUrls || lstUrls.isEmpty())
			return;
		
		CrawlController crawlController = this.getController(ConstantsCrawler.CRAWL_STORAGE + "cpi/detail", true,0);
//		CrawlController crawlController = this.getController(0);
		for(String link : lstUrls){
			crawlController.addSeed(link);
		}
		lstUrls.clear();
//		crawlController.addSeed("http://www.cpi.ac.cn/publish/default/tzgg/index.htm");
//		crawlController.addSeed("http://www.sda.gov.cn/WS01/CL0884/82508.html");
//		crawlController.addSeed("http://www.sda.gov.cn/WS01/CL0873/82217.html");
//		crawlController.addSeed("http://www.sda.gov.cn/WS01/CL0884/79811.html");
		
		crawlController.start(CpiStep2Crawler.class, 1);
	}

	@Override
	protected void doorError() {
		
	}

}

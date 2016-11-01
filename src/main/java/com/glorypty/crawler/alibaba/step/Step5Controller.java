package com.glorypty.crawler.alibaba.step;

import java.util.ArrayList;
import java.util.List;

import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.common.ConstantsCrawler;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * 
 * @author yiwen
 *
 */
public class Step5Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		if(null==lstUrls || lstUrls.isEmpty())
			return;
//		List<String> lstUrls2 = new ArrayList<String>();
//		lstUrls2.add("http://detail.1688.com/offer/41207712623.html?company=%E5%8D%95%E5%AE%9D%E6%B5%B7&contactName=%E5%8D%95%E5%AE%9D%E6%B5%B7&pageNum=2&phone=086%20575%2085196558&telphone=13906750161&zone=%E6%B5%99%E6%B1%9F%20%E7%BB%8D%E5%85%B4%E5%B8%82");
		
		
		/*CrawlController crawlController = this.getController(0);*/
		CrawlController crawlController = this.getController(ConstantsCrawler.CRAWL_STORAGE + "alibaba/", true, 0);
		for(String link : lstUrls){
			crawlController.addSeed(link);
		}
		lstUrls.clear();
		crawlController.start(Step5Crawler.class, 1);

	}

	@Override
	protected void doorError() {
		// TODO Auto-generated method stub

	}

}

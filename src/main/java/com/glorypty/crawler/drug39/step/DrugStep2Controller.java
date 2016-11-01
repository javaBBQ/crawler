/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.drug39.step.DrugStep2Controller.java
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.drug39.step;

import java.util.List;

import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.common.ConstantsCrawler;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * @author Andy
 * @date 2015年6月25日
 * @version 1.0
 */
public class DrugStep2Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		if(null==lstUrls || lstUrls.isEmpty())
			return;
//		CrawlController crawlController = this.getController(0);
		CrawlController crawlController = this.getController(ConstantsCrawler.CRAWL_STORAGE + "drug39/detail",true,0);
		for (String link : lstUrls) {
			crawlController.addSeed(link);
		}
		lstUrls.clear();
//		CrawlController crawlController = this.getController(0);
//		crawlController.addSeed("http://news.39.net/hygc/150623/4643295.html");
		crawlController.start(DrugStep2Crawler.class, 1);
	}

	@Override
	protected void doorError() {
		// TODO Auto-generated method stub

	}

}


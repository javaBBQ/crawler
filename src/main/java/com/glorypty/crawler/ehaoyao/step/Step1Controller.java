/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.yiyao.stepStep1Controller.java
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.ehaoyao.step;

import java.util.List;

import com.glorypty.crawler.base.BaseController;
import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * @author liuj
 * @date 2015年6月16日
 * @version 1.0
 */
public class Step1Controller extends BaseController {
	
	@Override
	public void door(List<String> lstUrls) throws Exception {
		if(null==lstUrls || lstUrls.isEmpty())
			return;
		
		CrawlController crawlController = this.getController(0);
		for(String link : lstUrls){
			crawlController.addSeed(link);
		}

		crawlController.start(Step1Crawler.class, 1);
	}

	@Override
	protected void doorError() {
	}
}


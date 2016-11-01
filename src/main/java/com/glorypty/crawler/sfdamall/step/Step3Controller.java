/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.sfda.step.Step3Controller.java	<2015年4月2日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.sfdamall.step;

import java.util.List;

import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.sfdamall.Constants;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * 爬虫控制器:内容详情
 * @Author yiwen
 * @Date 2015年4月2日 下午3:55:00
 */
public class Step3Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		if(null==lstUrls || lstUrls.isEmpty())
			return;
		
//		CrawlController crawlController = this.getController(0);
		CrawlController crawlController = this.getController(Constants.CRAWL_STORAGE, true, 0);
		for(String link : lstUrls){
			crawlController.addSeed(link);
		}
		lstUrls.clear();
		
		// 若两步爬取，则多线程；否则单线程即可
		crawlController.start(Step3Crawler.class, 1);
	}
	
	@Override
	public void doorError(){		
	}
	
}

/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.sfda.step.Step2Controller.java <2015年4月13日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.sfda.step;

import java.util.List;

import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.sfda.Constants;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * 爬虫控制器:内容页码
 * @Author hardy 
 * @Date 2015年4月13日 下午6:09:48
 * @version 1.0
 */
public class Step2Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		if(null==lstUrls || lstUrls.isEmpty())
			return;
		
		CrawlController crawlController = this.getController(Constants.CRAWL_STORAGE + "/step2", true, 1);
		for(String link : lstUrls){
			crawlController.addSeed(link);
		}
		lstUrls.clear();

		crawlController.start(Step2Crawler.class, 1);
	}
	
	@Override
	public void doorError(){		
	}

}

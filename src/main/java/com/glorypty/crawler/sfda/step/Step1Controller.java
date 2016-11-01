/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.Controller.java	<2015年4月2日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.sfda.step;

import java.util.List;

import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.sfda.Constants;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * 爬虫控制器:入口
 * @Author hardy 
 * @Date 2015年4月2日 上午11:29:10
 */
public class Step1Controller extends BaseController {
			
	@Override
	public void door(List<String> lstUrls) throws Exception {
		
		CrawlController crawlController = this.getController(0);
		crawlController.addSeed(Constants.URL_HOME);

		crawlController.start(Step1Crawler.class, 1);
	}
	
	@Override
	public void doorError(){		
	}
	
}

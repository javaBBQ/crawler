/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.hc360.step.Step0Controller.java <2015年12月17日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.hc360qg.step;

import java.util.List;

import com.glorypty.crawler.base.BaseController;
import com.glorypty.unwall.hc360.HcValidata;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * 爬虫列表分页控制器
 * @author ZhangLujun 
 * @Date 2015年12月17日 上午10:56:15
 * @version 1.0
 */
public class Step2Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		if(null==lstUrls || lstUrls.isEmpty())
			return;
		
		CrawlController crawlController = this.getController(0);
		for(String link : lstUrls){
			crawlController.addSeed(link);
		}
		
		lstUrls.clear();
		
//		if(!HcValidata.login())
//			return;
//		CrawlController crawlController = this.getController(0);
//		crawlController.addSeed("http://s.hc360.com/?mc=buyer&ss=542&ee=1&n=40");
//		crawlController.addSeed("http://s.hc360.com/?mc=buyer&ss=9534&ee=1&n=40");
		
		crawlController.start(Step2Crawler.class, 1);
	}

	@Override
	protected void doorError() {

	}

}

package com.glorypty.crawler.baiduyy.step;

import java.util.List;

import com.glorypty.crawler.baiduyy.BaiduyyConstants;
import com.glorypty.crawler.base.BaseController;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * 爬虫入口控制器
 * @author ZhangLujun
 *
 */
public class BaiduyyStep0Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		CrawlController crawlController = this.getController(0);
		
		List<String> urls = BaiduyyConstants.getUrlList();
		
		if(urls != null && urls.size() > 0){
			for (String url : urls) {
				crawlController.addSeed(url);
			}
		}
//		crawlController.addSeed(BaiduyyConstants.YYZH_URL_HOME);
		crawlController.start(BaiduyyStep0Crawler.class, 1);
	}

	@Override
	protected void doorError() {
		// TODO Auto-generated method stub

	}

}

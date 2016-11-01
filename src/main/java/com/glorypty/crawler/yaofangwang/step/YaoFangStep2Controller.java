package com.glorypty.crawler.yaofangwang.step;

import java.util.List;

import com.glorypty.crawler.base.BaseController;

import edu.uci.ics.crawler4j.crawler.CrawlController;
/**
 * 药房网爬虫列表页解析控制器
 * @author zhanglujun
 *
 */
public class YaoFangStep2Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		if(null==lstUrls || lstUrls.isEmpty())
			return;
		
		CrawlController crawlController = this.getController(0);
		
		for(String link : lstUrls){
			crawlController.addSeed(link);
		}
		lstUrls.clear();
//		crawlController.addSeed("http://yaochang.yaofangwang.com/599/");
//		crawlController.addSeed("http://yaochang.yaofangwang.com/746/");
		crawlController.start(YaoFangStep2Crawler.class, 1);
	}

	@Override
	protected void doorError() {
		// TODO Auto-generated method stub

	}

}

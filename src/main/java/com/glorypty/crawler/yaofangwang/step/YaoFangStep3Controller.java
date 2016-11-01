package com.glorypty.crawler.yaofangwang.step;

import java.util.List;

import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.common.ConstantsCrawler;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * 药房网爬虫详情页解析控制器
 * @author zhanglujun
 *
 */
public class YaoFangStep3Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		if(null==lstUrls || lstUrls.isEmpty())
			return;
//		CrawlController crawlController = this.getController(0);
		CrawlController crawlController = this.getController(ConstantsCrawler.CRAWL_STORAGE + "yaofangwang/detail", true, 0);
		for(String link : lstUrls){
			crawlController.addSeed(link);
		}
		lstUrls.clear();
//		crawlController.addSeed("http://yaochang.yaofangwang.com/599/");
//		crawlController.addSeed("http://yaochang.yaofangwang.com/746/");
//		crawlController.addSeed("http://yaodian.yaofangwang.com/339055/");
//		crawlController.addSeed("http://yiyuan.yaofangwang.com/501.html");
//		crawlController.addSeed("http://yaochang.yaofangwang.com/3419/");
//		crawlController.addSeed("http://yaodian.yaofangwang.com/340159/");
		crawlController.start(YaoFangStep3Crawler.class, 1);
	}

	@Override
	protected void doorError() {
		// TODO Auto-generated method stub

	}

}

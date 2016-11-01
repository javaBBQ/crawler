package com.glorypty.crawler.yaofangwang.step;

import java.util.List;

import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.yaofangwang.YaoFangConstants;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * 药房网爬虫入口控制器
 * @author zhanglujun
 *
 */
public class YaoFangStep1Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		CrawlController crawlController = this.getController(0);
		//药企
		crawlController.addSeed(YaoFangConstants.YC_HOME);
		//医院
		crawlController.addSeed(YaoFangConstants.YY_URL);
		//药房
		crawlController.addSeed(YaoFangConstants.YF_URL);
		
		crawlController.start(YaoFangStep1Crawler.class, 1);
	}

	@Override
	protected void doorError() {
		// TODO Auto-generated method stub
	}

}

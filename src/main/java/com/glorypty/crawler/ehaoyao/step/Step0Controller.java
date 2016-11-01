package com.glorypty.crawler.ehaoyao.step;

import java.util.List;

import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.ehaoyao.EHaoYaoConstants;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * 爬虫入口控制器
 * @author liuj
 *
 */
public class Step0Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		CrawlController crawlController = this.getController(0);
		crawlController.addSeed(EHaoYaoConstants.URL_HOME);
		crawlController.start(Step0Crawler.class, 1);
	}
	
	@Override
	protected void doorError() {
	}
}

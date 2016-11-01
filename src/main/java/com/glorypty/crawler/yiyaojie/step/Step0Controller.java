package com.glorypty.crawler.yiyaojie.step;

import java.util.List;

import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.yiyaojie.YiYaojieConstants;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * 爬虫入口控制器
 * @author yiwen
 *
 */
public class Step0Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		CrawlController crawlController = this.getController(0);
		List<String> list = YiYaojieConstants.getUrlList();
		if(list!=null&&list.size()>0){
			for (String url : list) {
				crawlController.addSeed(url);
			}
		}
		crawlController.start(Step0Crawler.class, 1);
	}

	@Override
	protected void doorError() {
		
	}

}

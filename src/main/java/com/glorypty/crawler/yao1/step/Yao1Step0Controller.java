package com.glorypty.crawler.yao1.step;

import java.util.List;

import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.yao1.Yao1Constants;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * 爬虫入口控制器
 * @author ZhangLujun
 *
 */
public class Yao1Step0Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		CrawlController crawlController = this.getController(0);
		
		List<String> urls = Yao1Constants.getUrlList();
		
		if(urls != null && urls.size() > 0){
			for (String url : urls) {
				crawlController.addSeed(url);
			}
		}
		crawlController.start(Yao1Step0Crawler.class, 1);
	}

	@Override
	protected void doorError() {
		// TODO Auto-generated method stub

	}

}
